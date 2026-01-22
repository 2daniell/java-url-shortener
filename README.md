# Encurtador de URL

[![Java](https://img.shields.io/badge/language-Java-blue.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Framework-green.svg)](https://spring.io/projects/spring-boot)
![Public](https://img.shields.io/badge/status-public-brightgreen.svg)

API REST de encurtamento de URLs desenvolvida em Java com Spring Boot, seguindo os princípios da Arquitetura Limpa. 
O projeto foi pensado com foco em separação de responsabilidades, testabilidade e performance.

A aplicação utiliza cache em memória com Caffeine, explorando request coalescing para evitar múltiplas consultas concorrentes ao banco de dados, reduzindo significativamente a latência em cenários de alta concorrência.

Tecnologias Utilizadas
----------------------
- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Caffeine Cache
- ApacheBench (testes de carga)


Funcionalidades
---------------
- Criar URL encurtada
- Remover URL 
- Redirecionar para a URL original
- Cache em memória para URLs mais acessadas
- ~~Extrair informações de quem acessou as URLs~~ (Futuro)


Endpoints Principais
--------------------
- `POST /api`
  Cria uma nova URL encurtada

- `GET /r/{slug}`
  Redireciona para a URL original

- `DELETE /api/{id}`
  Remove uma URL encurtada


Como Executar
-------------
1. Clone o repositório:
   ```bash
   git clone https://github.com/2daniell/java-url-shortener
   ```

2. Entre no diretório do projeto:
   ```bash
   cd java-url-shortener
   ```

3. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

A aplicação ficará disponível em:
http://localhost:8080


Testes
-----------
Foram realizados testes de carga locais utilizando ApacheBench:

- 300 usuários concorrentes
- 60.000 requisições

Resultados:
- Sem cache: ~300–400 ms de latência média
- Com cache (Caffeine + request coalescing): ~45–50 ms de latência média
- Throughput aproximado: ~6.000 requisições por segundo

Os testes demonstram ganho significativo de performance ao reduzir o acesso ao banco de dados sob alta concorrência.


Observações
-----------
- O H2 é utilizado apenas para fins de desenvolvimento e testes.
- O cache em memória (Caffeine), pode ser facilmente estendido para Redis em ambientes distribuídos.

