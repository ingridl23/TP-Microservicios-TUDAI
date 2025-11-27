# TP-Microservicios-TUDAI


En este microservicio lo que hicimos fue realizar las funcionalidades de Parada y Mapa, usamos un repositorio, un controller,un servicio , también implementamos el uso de un controller y de JWT. 
- En el **controller** hicimos todos los endpoints y utilizamos el service para ejecutar la lógica de negocio.
- En el **service** nos comunicamos con el repositorio. En el service vamos a hacer la lógica de negocio. 
- En el **repositorio** usamos el base de JPA (CRUD) -save() - findById() - findAll() - delete()

- **Este microservicio corre en el puerto 8080**

## ENDPOINTS : Microservicio de Parada-Mapa

### PARADA

##### GET `("/paradas")`
- Obtiene todas las paradas  
**PERMITIDO:** ('ROLE_USER', 'ROLE_ADMIN')

##### GET `("/{id}")`
- Obtiene la parada con la id  
**PERMITIDO:**  ('ROLE_USER', 'ROLE_ADMIN')

##### POST
- Crea una parada
**PERMITIDO:** ('ROLE_ADMIN')

##### PUTMAPPING `("/{id}")`
- Modifica los datos de una parada  
**PERMITIDO:** ('ROLE_ADMIN')

##### DELETEMAPPING `("/{id}")`
- Borra la parada con la id  
**PERMITIDO:** ('ROLE_ADMIN')

##### GET `("/{id}/monopatines")`
- Obtiene todos los monopatines de una parada 
**PERMITIDO:** ('ROLE_ADMIN')

##### GET `("/{id}/monopatin/{idMonopatin}")`
- Corrobora si en una parada especifica se encuentra un monopatin en especifico 
**PERMITIDO:** ('ROLE_ADMIN')

### MAPA

##### GET `("/mapas")`
- Obtiene todos los mapas 
**PERMITIDO:** ('ROLE_ADMIN')

##### GET `("/{id}")`
- Obtiene el mapa con la id
**PERMITIDO:**  ('ROLE_ADMIN')

##### GET `("{idMapa}/paradas")`
- Lista las paradas de un mapa 
**PERMITIDO:** ('ROLE_USER','ROLE_ADMIN')

##### POST
- Crea un mapa
**PERMITIDO:** ('ROLE_ADMIN')

##### PUT MAPPING `("/{id}")`
- Modifica los datos de un mapa  
**PERMITIDO:** ('ROLE_ADMIN')

##### PATCH MAPPING `("/{idMapa}/paradas/{idParada}")`
- Agrega una parada a un mapa 
**PERMITIDO:** ('ROLE_ADMIN')

##### DELETEMAPPING `("/{idMapa}/paradas/{idParada}")`
- Quita una parada de un mapa
**PERMITIDO:** ('ROLE_ADMIN')

##### DELETEMAPPING `("/{id}")`
- Borra el mapa con la id  
**PERMITIDO:** ('ROLE_ADMIN')

