# CustomerConnect

API REST desenvolvida com Spring Boot para gerenciamento de clientes.

O projeto foi constru�do com foco em aprendizado de backend, cobrindo opera��es de CRUD, pagina��o, filtros, tratamento de erros HTTP e valida��es de regra de neg�cio.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- H2 Database
- Maven

## Funcionalidades

- Criar clientes
- Listar clientes com pagina��o
- Filtrar clientes por CPF e email
- Atualizar clientes por id
- Remover clientes por id
- Impedir duplicidade de CPF e email
- Retornar status HTTP coerentes para erro de valida��o e conflito de dados

## Estrutura do projeto

O projeto segue uma separa��o simples por responsabilidade:

- `controller`: recebe as requisi��es HTTP e monta as respostas
- `service`: concentra as regras de neg�cio
- `repository`: faz o acesso aos dados com Spring Data JPA
- `entity`: representa a tabela de clientes
- `dto`: define os objetos de entrada e sa�da da API

## Endpoints

### Criar cliente

`POST /customers`

Exemplo de body:

```json
{
  "name": "Maria Silva",
  "cpf": "12345678900",
  "email": "maria@email.com",
  "phoneNumber": "11999999999"
}
```

Resposta esperada:

- `201 Created` quando o cliente for criado com sucesso
- `409 Conflict` quando CPF ou email j� estiverem cadastrados

### Listar clientes

`GET /customers`

Par�metros suportados:

- `page` - p�gina desejada. Padr�o: `0`
- `pageSize` - quantidade de registros por p�gina. Padr�o: `10`
- `orderBy` - ordena��o por data de cria��o: `asc` ou `desc`
- `cpf` - filtro opcional por CPF
- `email` - filtro opcional por email

Exemplo:

```http
GET /customers?page=0&pageSize=10&orderBy=desc&cpf=12345678900
```

Resposta esperada:

- `200 OK` com lista paginada de clientes
- `400 Bad Request` quando `page`, `pageSize` ou `orderBy` forem inv�lidos

### Atualizar cliente

`PUT /customers/{customerId}`

Exemplo de body:

```json
{
  "name": "Maria Souza",
  "email": "maria.souza@email.com"
}
```

Comportamento atual:

- atualiza apenas os campos enviados no body
- atualiza o campo de data de modifica��o
- retorna `404 Not Found` se o cliente n�o existir
- retorna `409 Conflict` se o novo CPF ou email j� pertencer a outro cliente

### Deletar cliente

`DELETE /customers/{customerId}`

Resposta esperada:

- `204 No Content` quando o cliente for removido com sucesso
- `404 Not Found` quando o cliente n�o existir

## Regras de neg�cio importantes

- `id` � gerado automaticamente pelo banco
- `cpf` deve ser �nico
- `email` deve ser �nico
- a unicidade � protegida tanto na aplica��o quanto no banco de dados
- par�metros inv�lidos de pagina��o retornam erro `400 Bad Request`

## Como executar o projeto

### Pr�-requisitos

- Java 17 instalado
- Maven Wrapper dispon�vel no projeto

### Passos

1. Clone o reposit�rio
2. Entre na pasta do projeto
3. Execute o comando:

```bash
./mvnw spring-boot:run
```

No Windows, use:

```bat
mvnw.cmd spring-boot:run
```

A aplica��o ser� iniciada localmente.

## Banco de dados

O projeto usa H2 como banco em mem�ria durante a execu��o.

Se desejar, voc� pode evoluir o projeto depois para PostgreSQL ou MySQL.

## Aprendizados praticados neste projeto

- constru��o de API REST com Spring Boot
- modelagem b�sica de entidade
- uso de DTOs
- pagina��o com `PageRequest`
- filtros com `@Query`
- valida��o de entrada
- tratamento de erros HTTP
- preven��o de duplicidade de dados
- organiza��o em camadas

## Melhorias futuras

- adicionar testes unit�rios e de integra��o
- criar exceptions customizadas para a API
- adicionar documenta��o com Swagger/OpenAPI
- integrar com banco relacional persistente
- implementar valida��es com Bean Validation
- criar endpoint para buscar cliente por id

## Autor

Projeto desenvolvido por [Danillo Santos].
