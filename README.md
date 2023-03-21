# Candidates db REST app
SpringBoot app using Java for managing the candidates' database, consisting of two tables - candidates and theirs connected 
technologies and also connection between them with a rating and a note. The database used is in memory H2.

### Production links:
> [Swagger](https://db-uchazeci-production.up.railway.app/swagger-ui/index.html)  
> [Actuator](https://db-uchazeci-production.up.railway.app/actuator)

### Candidates management:  
- list
- detail, including the list of technologies
- create, delete, update

### Technologies management:
- list
- detail
- create, delete, update

### Additional functionalities
In the name of learning new stuff, I have also added following:
- Swagger
- Validations
- Custom response
- Content negotiation
- Actuator

Although it works locally, I wasn't able to successfully execute queries directly from Swagger on the Railway 
production due to Cross-Origin Resource Sharing.


