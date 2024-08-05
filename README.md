# BureauTest Application

## Descrição

A aplicação BureauTest é um sistema de gerenciamento de documentos e tradutores. A aplicação permite o upload de arquivos CSV contendo informações sobre documentos e tradutores, além de integrar-se opcionalmente com a API OpenAI para classificar automaticamente o conteúdo do documento na localidade correspondente.

## Funcionalidades

- Upload de arquivos CSV para documentos e tradutores.
- Listagem de tradutores e documentos.
- Edição e remoção de tradutores e documentos.
- Paginação e ordenação de listas.
- Integração com a API OpenAI para detecção de idioma de documentos.
- Interface de usuário amigável e responsiva utilizando Vue.js e Bootstrap-Vue.

## Tecnologias Utilizadas

- Backend: Java, Spring Boot, OpenCSV, Hibernate, JPA, PostgreSQL, WebClient
- Frontend: Vue.js, Bootstrap-Vue, Axios
- API: OpenAI

## Requisitos

- Java 11 ou superior
- Node.js e npm
- PostgreSQL
- Conta na OpenAI com chave de API

## Instalação

### Backend

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/bureautest.git
    cd bureautest
    ```

2. Crie as tabelas no banco de dados PostgreSQL executando o script `script.sql`:
    - Usando a linha de comando `psql`:
        ```bash
        psql -U seu-usuario -d nome-do-banco-de-dados -f script.sql
        ```
        Substitua `seu-usuario` pelo seu nome de usuário do PostgreSQL e `nome-do-banco-de-dados` pelo nome do seu banco de dados.
    
    - Usando uma ferramenta de administração (como pgAdmin):
        1. Abra a ferramenta de administração do PostgreSQL (pgAdmin, DBeaver, etc.).
        2. Conecte-se ao seu banco de dados.
        3. Abra uma nova janela de consulta e carregue o arquivo `script.sql`.
        4. Execute o script.

3. Configure o banco de dados PostgreSQL e atualize `application.properties` com suas credenciais:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/bureautest
    spring.datasource.username=seu-usuario
    spring.datasource.password=sua-senha
    spring.jpa.hibernate.ddl-auto=update

    openai.api.key=SUA_CHAVE_DE_API_OPENAI
    ```

4. Compile e execute o backend:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```

### Frontend

1. Navegue até o diretório `frontend`:
    ```bash
    cd frontend/bureautest
    ```

2. Instale as dependências:
    ```bash
    npm install
    ```

3. Inicie o servidor de desenvolvimento:
    ```bash
    npm run dev
    ```

## Uso

### Upload de CSV

1. Navegue até a página de tradutores ou documentos.
2. Clique no botão "Upload CSV".
3. Selecione o arquivo CSV desejado e clique em "Upload".

### Listagem, Edição e Remoção

1. Navegue até a página de tradutores ou documentos.
2. Utilize os botões "Edit" e "Delete" para editar ou remover itens.
3. Use os controles de paginação e ordenação para navegar pelas listas.

### Integração com OpenAI

A integração com a API OpenAI é utilizada para detectar automaticamente o idioma dos documentos durante o upload de arquivos CSV. Certifique-se de configurar corretamente sua chave de API no arquivo `application.properties`.

## Imagens

### Tela Home
![Home](https://github.com/GuilhermeSimbalista/bureau-test/blob/main/img/Home.JPG)

### Tela de Tradutores
![Tradutor](https://github.com/GuilhermeSimbalista/bureau-test/blob/main/img/Tradutor.JPG)

### Tela de Documentos
![Documentos](https://github.com/GuilhermeSimbalista/bureau-test/blob/main/img/Documento.JPG)

### Formulário
![Home](https://github.com/GuilhermeSimbalista/bureau-test/blob/main/img/Formulario.JPG)


