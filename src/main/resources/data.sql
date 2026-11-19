-- Inserindo um motorista e um passageiro
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('Aquila Silva', 'aquila@email.com', '34995920713', TRUE);
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('João Passageiro', 'joao@email.com', '34999999999', FALSE);
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('Ygor Teodoro', 'ygor@email.com', '34999999999', FALSE);
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('Iara', 'iara@email.com', '34999959999', TRUE);
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('Oliver', 'oliver@email.com', '34999990999', FALSE);

-- Inserindo uma solicitação do João (ID 2) para a carona (ID 1)
INSERT INTO solicitacoes (carona_id, passageiro_id, status) VALUES (1, 2, 'PENDENTE');

-- Inserindo novas caronas oferecidas pelos motoristas cadastrados (IDs 1 e 4)
INSERT INTO caronas (motorista_id, origem, destino, data_hora, vagas_disponiveis)
VALUES (1, 'Campus IFTM Centro', 'Uberaba Shopping', '2026-11-16 08:00:00', 2);

INSERT INTO caronas (motorista_id, origem, destino, data_hora, vagas_disponiveis)
VALUES (4, 'Santa Mônica', 'Campus IFTM Centro', '2026-11-16 07:30:00', 3);

