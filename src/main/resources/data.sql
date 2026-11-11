-- Inserindo um motorista e um passageiro
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('Aquila Silva', 'aquila@email.com', '34998020713', TRUE);
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('João Passageiro', 'joao@email.com', '34999999999', FALSE);

-- Inserindo uma carona oferecida pelo Aquila (ID 1)
INSERT INTO caronas (motorista_id, origem, destino, data_hora, vagas_disponiveis) 
VALUES (1, 'Campus IFTM Centro', 'Santa Mônica', '2026-11-15 22:30:00', 3);

-- Inserindo uma solicitação do João (ID 2) para a carona (ID 1)
INSERT INTO solicitacoes (carona_id, passageiro_id, status) VALUES (1, 2, 'PENDENTE');