# 4ª edição Curso de Férias 2019 - Construindo APIs REST com Spring

```

```

* ## Estrutura das aulas

	* **Aula 1**  
  		* [Slides - aula 1](https://docs.google.com/presentation/d/1DpL6uBw-hp5ox8unehsFdmAvYbtkWVhVYlh0zb3bSNY/edit?usp=sharing)
    * **Aula 2**  
  		* [Slides - aula 2](https://docs.google.com/presentation/d/14D4vZ0f43wYUHp5pKhfuydlKwps1Mpc02Wn5BqxmnSc/edit#slide=id.g3ea92d5bb8_1_0)
    	* [Branch - aula 2]() - Criação do projeto inicial com [Spring Initializr](https://start.spring.io/)
    * **Aula 3**
    	* JPA
        * Spring Data
        * H2 console
        * Criação da base de dados a partir das entidades. Conexão estabelecida com banco de dados
        * [Branch - aula 3]()
    * **Aula 4**
    	* Injfeção de dependências
    	* Criação da estrutura do pacote **business** com métodos de consulta *findById* e *findAll*
        * [Branch - aula 4]()
    * **Aula 5**
    	* Criação da estrutura do pacote **service** com métodos de consulta *findById* e *findAll*
        * Criação dos *DTOs* de saída
        * Criação da estrutura do pacote **controller** com requisições *GET* (*findById* e *findAll*)
        * Anotações do Jackson para formatação do JSON
        * Requisições via Postman
        * [Branch - aula 5]()
    * **Aula 6**
    	* Criação dos métodos *save* e *deleteById*
        * Criação dos *DTOs* de entrada
        * Criação das requisições *POST*, *PUT* e *DELETE*
        * [Branch - aula 6]()
    * **Aula 7**
    	* Tratamento de códigos de retorno HTTP
        * *Exception handler*
        * Criação de validações na camada **business**
        * Validações nos *requests* 
        * [Branch - aula 7]()
    * **Aula 8**
    	* Testes unitários da camada **controller** com *mock*
        * [Branch - aula 8]()
    * **Aula 9**
    	* Revisão
        * [Branch - aula 9]()
    * **Aula 10**
    	* [Branch - aula 10]()        

* ## Projeto
	* ### Especificações técnicas
		* **Linguagem de programação:** Java - jdk1.8.0_152 ou superior
    	* **Gerenciador de dependências:** apache-maven-3.0.4
    	* **Spring:** 2.1.1.RELEASE
        * **Banco de dados:** H2 database - http://localhost:8080/h2
	
    * ### Documentação
    	* **Swagger**: [petstore.yaml](etc/petstore.yaml)
        
	* ### Modelagem    
  	![modelagem](etc/modelagem.png)

	* ### Representações
	
    	Os modelos de entrada e saída são representados no formato JSON. Pacote para importação no **Postman** [PetStore.postman_collection.json](etc/PetStore.postman_collection.json)
    
      * **Cliente** 
      
      	*ClienteRequestDTO*
      	```json
        {
          "nome": "Pedro"
        }              
        ```    
      
        *ClienteResponseDTO*
        ```json
        { 
          "id": 1,
          "nome": "Pedro"
        }
      	```     
      * **Espécie**
      
      	*EspecieRequestDTO*
        ```json
        {
          "descricao": "Cachorro"
        }
        ```
        
        *EspecieResponseDTO*
        ```json
        {
          "id": 1,
          "descricao": "Cachorro"
        }
        ```
      * **Pet**
      
      	*PetRequestDTO*
      	```json
        {
          "nome": "Rex",
          "dataNascimento": "01/01/2019",
          "idCliente": 1,
          "idEspecie": 1
        }
        ```
        
        *PetResponseDTO*
        ```json
        {
          "id": 1,
          "nome": "Rex",
          "dataNascimento": "01/01/2019",
          "cliente": {
            "id": 1,
            "nome": "Pedro"
          },
          "especie": {
            "id": 1,
            "descricao": "Cachorro"
          }
        }
        ```
      * **Serviço**
      
      	*ServicoRequestDTO*
      	```json
        {
          "observacao": "Consulta",
          "idTipoServico": 1,
          "valor": 80.00,
          "idPet": 1
        }
        ```
        
        *ServicoResponseDTO*
        ```json
        {
          "id": 1,
          "observacao": "Consulta",
          "dataHora": "11/01/2019 12:38:17",
          "tipoServico": "Consulta",
          "valor": 80.00,
          "pet": {
            "id": 1,
            "nome": "Rex",
            "dataNascimento": "01/01/2019",
            "cliente": {
              "id": 1,
              "nome": "Pedro"
            },
            "especie": {
              "id": 1,
              "descricao": "Cachorro"
            }
          }
        }
        ```     
        	
	* ### Requisições        

      * **Cliente** 

      Método | URL                                             | Entrada             | Saída 
      ------ | ----------------------------------------------- | ------------------- | ------ |
      POST   | http://localhost:8080/api/v1/clientes           | *ClienteRequestDTO* | 201 (Created)
      GET    | http://localhost:8080/api/v1/clientes           |                     | 200 (OK) Lista *ClienteResponseDTO*
      GET    | http://localhost:8080/api/v1/clientes/{id}      |                     | 200 (OK) *ClienteResponseDTO*
      GET    | http://localhost:8080/api/v1/clientes/{id}/pets |                     | 200 (OK) Lista *PetResponseDTO*
      PUT    | http://localhost:8080/api/v1/clientes/{id}      | *ClienteRequestDTO* | 204 (No Content)
      DELETE | http://localhost:8080/api/v1/clientes/{id}      |                     | 204 (No Content)

      * **Espécie**

      Método | URL                                             | Entrada             | Saída 
      ------ | ----------------------------------------------- | ------------------- | ------ |
      POST   | http://localhost:8080/api/v1/especies           | *EspecieRequestDTO* | 201 (Created)
      GET    | http://localhost:8080/api/v1/especies           |                     | 200 (OK) Lista *EspecieResponseDTO*
      GET    | http://localhost:8080/api/v1/especies/{id}      |                     | 200 (OK) *EspecieResponseDTO*
      GET    | http://localhost:8080/api/v1/especies/{id}/pets |                     | 200 (OK) Lista *PetResponseDTO*
      PUT    | http://localhost:8080/api/v1/especies/{id}      | *EspecieRequestDTO* | 204 (No Content)
      DELETE | http://localhost:8080/api/v1/especies/{id}      |                     | 204 (No Content)

      * **Pet**

      Método | URL                                             | Entrada          | Saída 
      ------ | ----------------------------------------------- | ---------------- | ------ |
      POST   | http://localhost:8080/api/v1/pets               | *PetRequestDTO*  | 201 (Created)
      GET    | http://localhost:8080/api/v1/pets               |                  | 200 (OK) Lista *PetResponseDTO*
      GET    | http://localhost:8080/api/v1/pets/{id}          |                  | 200 (OK) *PetResponseDTO*
      GET    | http://localhost:8080/api/v1/pets/{id}/servicos |                  | 200 (OK) Lista *ServicoResponseDTO*
      PUT    | http://localhost:8080/api/v1/pets/{id}          | *PetRequestDTO*  | 204 (No Content)
      DELETE | http://localhost:8080/api/v1/pets/{id}          |                  | 204 (No Content)

      * **Serviço**

      Método | URL                                                                                            | Entrada             | Saída 
      ------ | ---------------------------------------------------------------------------------------------- | ------------------- | ------ |
      POST   | http://localhost:8080/api/v1/servicos      													  | *ServicoRequestDTO* | 201 (Created)
      GET    | http://localhost:8080/api/v1/servicos      													  |                     | 200 (OK) Lista *ServicoResponseDTO*
      GET    | http://localhost:8080/api/v1/servicos/{id} 													  |                     | 200 (OK) *ServicoResponseDTO*
      GET    | http://localhost:8080/api/v1/servicos/buscaPorData?dataInicial=dd/MM/yyyy&dataFinal=dd/MM/yyyy |                     | 200 (OK) Lista *ServicoResponseDTO*
      PUT    | http://localhost:8080/api/v1/servicos/{id} 													  | *ServicoRequestDTO* | 204 (No Content)
      DELETE | http://localhost:8080/api/v1/servicos/{id} 													  |                     | 204 (No Content)
