CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    is_motorista BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_usuarios_id_motorista UNIQUE (id, is_motorista)
);

CREATE TABLE caronas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    motorista_id BIGINT NOT NULL,
    motorista_is_motorista BOOLEAN NOT NULL DEFAULT TRUE CHECK (motorista_is_motorista = TRUE),
    origem VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    vagas_disponiveis INT NOT NULL,
    CONSTRAINT uq_carona_motorista UNIQUE (motorista_id),
    CONSTRAINT fk_motorista FOREIGN KEY (motorista_id, motorista_is_motorista)
        REFERENCES usuarios(id, is_motorista)
);

CREATE TABLE solicitacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    carona_id BIGINT NOT NULL,
    passageiro_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    CONSTRAINT fk_carona FOREIGN KEY (carona_id) REFERENCES caronas(id),
    CONSTRAINT fk_passageiro FOREIGN KEY (passageiro_id) REFERENCES usuarios(id),
    CONSTRAINT uq_solicitacao_carona_passageiro UNIQUE (carona_id, passageiro_id)
);