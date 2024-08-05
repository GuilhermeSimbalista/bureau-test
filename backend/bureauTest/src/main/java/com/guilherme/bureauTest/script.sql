-- Criação da tabela 'translator'
CREATE TABLE translator (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    source_language VARCHAR(255) NOT NULL,
    target_language VARCHAR(255) NOT NULL
);

-- Criação da tabela 'document'
CREATE TABLE document (
    id BIGSERIAL PRIMARY KEY,
    subject VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    locale VARCHAR(255),
    author VARCHAR(255) NOT NULL,
    translator_id BIGINT,
    FOREIGN KEY (translator_id) REFERENCES translator(id)
);
