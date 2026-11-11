CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    is_motorista BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE caronas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    motorista_id BIGINT NOT NULL,
    origem VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    vagas_disponiveis INT NOT NULL,
    CONSTRAINT fk_motorista FOREIGN KEY (motorista_id) REFERENCES usuarios(id)
);

CREATE TABLE solicitacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    carona_id BIGINT NOT NULL,
    passageiro_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    CONSTRAINT fk_carona FOREIGN KEY (carona_id) REFERENCES caronas(id),
    CONSTRAINT fk_passageiro FOREIGN KEY (passageiro_id) REFERENCES usuarios(id)
);