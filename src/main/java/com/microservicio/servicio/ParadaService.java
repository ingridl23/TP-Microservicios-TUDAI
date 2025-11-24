package com.microservicio.servicio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.*;


import com.microservicio.dto.MonopatinDTO;
import com.microservicio.dto.ParadaDTO;

import com.microservicio.modelo.Parada;
import com.microservicio.repositorio.ParadaRepository;

@Service
public class ParadaService {

    private final ParadaRepository repository;
    private final RestTemplate restTemplate;
    private static final String MONOPATINES_URL = "http://localhost:8081/monopatines?paradaId=";/*Extraer la URL del microservicio en una constante para evitar hardcodeo:*/

    public ParadaService(ParadaRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public List<ParadaDTO> findAll() {
        List<Parada> paradas = repository.findAll();
        return paradas.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ParadaDTO findById(Long id) {
        Parada parada = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parada no encontrada"));

        return convertToDTO(parada);
    }
//registra una nueva parada en la BD y no necesita de los monopatines
    public ParadaDTO create(ParadaDTO dto) {
        Parada parada = convertToEntity(dto);
        Parada saved = repository.save(parada);
        return convertToDTO(saved);
        
    }

    public ParadaDTO update(Long id, ParadaDTO dto) {
        Parada existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la parada"));

        existing.setLatitud(dto.getLatitud());
        existing.setLongitud(dto.getLongitud());

        return convertToDTO(repository.save(existing));
    }

    public void delete(Long id) {
        Parada existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la parada"));
        repository.delete(existing);
    }

    public List<MonopatinDTO> getMonopatinesPorRest(Long idParada) {
        String url = MONOPATINES_URL + idParada;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = null;

        if (auth != null && auth.getCredentials() != null) {
            token = auth.getCredentials().toString();
        }

        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<Void> entity = new HttpEntity<>(headers);;
        ResponseEntity<MonopatinDTO[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                MonopatinDTO[].class
        );

        MonopatinDTO[] body = response.getBody();

        List<MonopatinDTO> lista = new ArrayList<>();
        if (body != null) {
            for (MonopatinDTO m : body) {
                lista.add(m);
            }
        }

        return lista;
    }


    public boolean getMonopatinByParada(Long idParada, Long idMonopatin) {
        return getMonopatinesPorRest(idParada).stream()
                .anyMatch(m -> m.getId().equals(idMonopatin));
    }

    private ParadaDTO convertToDTO(Parada parada) {
        return new ParadaDTO(parada.getId(), parada.getLatitud(), parada.getLongitud(), new ArrayList<>());
    }

    private Parada convertToEntity(ParadaDTO dto) {
        Parada parada = new Parada();
        parada.setLatitud(dto.getLatitud());
        parada.setLongitud(dto.getLongitud());
        return parada;
    }

    @Configuration
    public static class RestTemplateConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
      
}

	 

