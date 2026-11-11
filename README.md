# Campus Car

## O Problema
A mobilidade urbana entre os campi universitários e as áreas residenciais frequentemente apresenta desafios de custo, tempo e infraestrutura. O Campus Car visa solucionar a dificuldade de locomoção dos alunos do IFTM, conectando estudantes que possuem veículos e disponibilidade de assentos com aqueles que precisam de transporte para o mesmo destino ou rota. 

## Requisitos do Sistema
* **Gestão de Usuários:** Cadastro de usuários contendo nome, e-mail, telefone e um identificador booleano para distinguir motoristas de passageiros.
* **Gestão de Caronas:** Usuários motoristas podem ofertar caronas informando origem, destino, data/hora e quantidade de vagas disponíveis.
* **Gestão de Solicitações:** Usuários passageiros podem solicitar participação em caronas disponíveis. A solicitação deve registrar o status (PENDENTE, ACEITA, RECUSADA).
* **Validação de Dados:** Garantia de integridade das informações via Spring Validation (sem implementação de Spring Security ou sistemas de login nesta etapa).
* **Persistência:** Utilização de banco de dados relacional em memória (H2) operado via JdbcTemplate, com inicialização por scripts SQL.