-- Inserindo um motorista e um passageiro
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('Aquila Silva', 'aquila@email.com', '34995920713', TRUE);
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('João Passageiro', 'joao@email.com', '34999999999', FALSE);
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('Ygor Teodoro', 'ygor@email.com', '34999999999', FALSE);
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('Iara', 'iara@email.com', '34999959999', TRUE);
INSERT INTO usuarios (nome, email, telefone, is_motorista) VALUES ('Oliver', 'oliver@email.com', '34999990999', FALSE);

-- Caronas e solicitações de exemplo (motoristas: Aquila e Iara; passageiros: João, Ygor e Oliver)
INSERT INTO caronas (motorista_id, origem, destino, disponibilidade, vagas_disponiveis)
VALUES ((SELECT id FROM usuarios WHERE email = 'aquila@email.com'), 'Centro', 'Campus', 'Dirijo de segunda a quarta pela manhã.', 3);

INSERT INTO caronas (motorista_id, origem, destino, disponibilidade, vagas_disponiveis)
VALUES ((SELECT id FROM usuarios WHERE email = 'iara@email.com'), 'Campus', 'Centro', 'Disponível para voltar ao Centro nos dias úteis à tarde.', 2);

INSERT INTO solicitacoes (carona_id, passageiro_id, status)
VALUES (
	(SELECT id FROM caronas WHERE origem = 'Centro' AND destino = 'Campus'
	   AND motorista_id = (SELECT id FROM usuarios WHERE email = 'aquila@email.com')),
	(SELECT id FROM usuarios WHERE email = 'joao@email.com'),
	'PENDENTE'
);

INSERT INTO solicitacoes (carona_id, passageiro_id, status)
VALUES (
	(SELECT id FROM caronas WHERE origem = 'Centro' AND destino = 'Campus'
	   AND motorista_id = (SELECT id FROM usuarios WHERE email = 'aquila@email.com')),
	(SELECT id FROM usuarios WHERE email = 'ygor@email.com'),
	'PENDENTE'
);

INSERT INTO solicitacoes (carona_id, passageiro_id, status)
VALUES (
	(SELECT id FROM caronas WHERE origem = 'Campus' AND destino = 'Centro'
	   AND motorista_id = (SELECT id FROM usuarios WHERE email = 'iara@email.com')),
	(SELECT id FROM usuarios WHERE email = 'oliver@email.com'),
	'PENDENTE'
);


