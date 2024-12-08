------------------------------------------------------------------
-- Etapa 2
-- SIBD 24/25
-- Grupo 03
-- Marcelo Munteanu 56359 TP12 (33.3%)
-- Martin Berninger 64627 TP16 (33.3%)
-- António Domingues 51994 TP15 (33.3%)
------------------------------------------------------------------

DROP TABLE loja CASCADE CONSTRAINTS;
DROP TABLE pessoa CASCADE CONSTRAINTS;
DROP TABLE empregado;
DROP TABLE cliente;
DROP TABLE equipamento;
DROP TABLE ficha_de_equipamento CASCADE CONSTRAINTS;
DROP TABLE fatura;
------------------------------------------------------------------
CREATE TABLE loja (
    nipc        NUMBER(9),
    nome        VARCHAR(40) NOT NULL,
    telefone    NUMBER(9) NOT NULL,
    email       VARCHAR(40) NOT NULL,
--
    CONSTRAINT pk_loja 
        PRIMARY KEY (nipc),
--
    CONSTRAINT ck_loja_nipc 
        CHECK (nipc > 0),
--
    CONSTRAINT ck_loja_telefone 
        CHECK (telefone > 0),
--    
    CONSTRAINT un_loja_nome 
        UNIQUE (nome),
--
    CONSTRAINT un_loja_telefone 
        UNIQUE (telefone),
--
    CONSTRAINT un_loja_email 
        UNIQUE (email)
);
------------------------------------------------------------------
CREATE TABLE ficha_de_equipamento (
    ean                 CHAR(13) NOT NULL,
    modelo              VARCHAR(40) NOT NULL,
    tipo                VARCHAR(40) NOT NULL,
    marca               VARCHAR(40) NOT NULL,
    preco_lancamento    NUMBER(7,2) NOT NULL,
    ano_de_lancamento   NUMBER(4) NOT NULL,
--
    CONSTRAINT pk_ficha_de_equipamento 
        PRIMARY KEY (ean),
--
    CONSTRAINT ck_ficha_de_equipamento_ean 
        CHECK (ean > 0),
--
    CONSTRAINT ck_ficha_de_equipamento_preco 
        CHECK (preco_lancamento > 0),
--
    CONSTRAINT ck_ficha_de_equipamento_ano 
        CHECK (ano_de_lancamento <= 2024)
);
------------------------------------------------------------------
CREATE TABLE pessoa (
    nif         CHAR(9),
    nome        VARCHAR(100) NOT NULL,
    telemovel   CHAR(9),
    genero      CHAR(1) NOT NULL,
--
    CONSTRAINT pk_pessoa 
        PRIMARY KEY (nif),
--
    CONSTRAINT ck_pessoa_nif 
        CHECK (nif > 0),
--
    CONSTRAINT ck_pessoa_genero 
        CHECK (genero = 'M' OR genero = 'F'),
--
    CONSTRAINT ck_pessoa_telemovel 
        CHECK (telemovel > 0),
--
    CONSTRAINT un_pessoa_telemovel 
        UNIQUE (telemovel)
);
------------------------------------------------------------------
CREATE TABLE empregado (
    idade          INTEGER NOT NULL,
    nif            CHAR(9) NOT NULL,
    nic            CHAR(8) NOT NULL,
    numero_interno CHAR(5) NOT NULL,
--
    CONSTRAINT pk_empregado 
        PRIMARY KEY (nif),
--
    CONSTRAINT fk_empregado_pessoa 
        FOREIGN KEY (nif) REFERENCES pessoa (nif) ON DELETE CASCADE,
--
    CONSTRAINT ck_empregado_idade 
        CHECK (idade >= 16),
--
    CONSTRAINT un_empregado_nic 
        UNIQUE (nic),
--
    CONSTRAINT un_empregado_numero 
        UNIQUE (numero_interno)
);
------------------------------------------------------------------
CREATE TABLE cliente (
    nif CHAR(9),
--
    CONSTRAINT pk_cliente 
        PRIMARY KEY (nif),
--
    CONSTRAINT fk_cliente_nif 
        FOREIGN KEY (nif) REFERENCES pessoa (nif) ON DELETE CASCADE
);
------------------------------------------------------------------
CREATE TABLE equipamento (
    ean             CHAR(13) NOT NULL,
    numero_exemplar INTEGER NOT NULL,
    estado          CHAR(3) NOT NULL,
    preco           INTEGER NOT NULL,
    data_na_loja    DATE,
--
    CONSTRAINT pk_equipamento 
        PRIMARY KEY (ean, numero_exemplar),
--
    CONSTRAINT fk_equipamento 
        FOREIGN KEY (ean) REFERENCES ficha_de_equipamento (ean) ON DELETE CASCADE,
--
    CONSTRAINT ck_equipamento_ean 
        CHECK (ean > 0),
--
    CONSTRAINT ck_equipamento_preco 
        CHECK (preco > 0),
--
    CONSTRAINT ck_equipamento_estado 
        CHECK (estado = 'BOM' OR estado = 'MAU'),
--
    CONSTRAINT un_equipamento_exemplar 
        UNIQUE (numero_exemplar)
);
------------------------------------------------------------------
CREATE TABLE fatura (
    nipc                NUMBER(9) NOT NULL,
    numero_sequencial   INTEGER NOT NULL,
    data_fatura         DATE,
--
    CONSTRAINT pk_fatura 
        PRIMARY KEY (nipc, numero_sequencial),
--
    CONSTRAINT fk_fatura 
        FOREIGN KEY (nipc) REFERENCES loja(nipc) ON DELETE CASCADE,
--
    CONSTRAINT ck_fatura_nipc 
        CHECK (nipc > 0)
);
------------------------------------------------------------------
ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY';
------------------------------------------------------------------
INSERT INTO loja (nipc, nome, telefone, email)
    VALUES (123456789, 'Loja A', 912345678, 'contato@lojaa.com');
------------------------------------------------------------------
INSERT INTO loja (nipc, nome, telefone, email)
    VALUES (987654321, 'Loja B', 923456789, 'contato@lojab.com');
------------------------------------------------------------------
INSERT INTO ficha_de_equipamento (ean, modelo, tipo, marca, preco_lancamento, ano_de_lancamento)
    VALUES ('1112223334445', 'iPhone X', 'smartphone', 'Apple', 999.99, 2018);
------------------------------------------------------------------
INSERT INTO ficha_de_equipamento (ean, modelo, tipo, marca, preco_lancamento, ano_de_lancamento)
    VALUES ('2223334445556', 'Galaxy S10', 'smartphone', 'Samsung', 899.99, 2019);
------------------------------------------------------------------
INSERT INTO pessoa (nif, nome, telemovel, genero)
    VALUES ('123456789', 'Joao Silva', '912345678', 'M');
------------------------------------------------------------------
INSERT INTO pessoa (nif, nome, telemovel, genero)
    VALUES ('987654321', 'Maria Pereira', '923456789', 'F');
------------------------------------------------------------------
INSERT INTO empregado (idade, nif, nic, numero_interno)
    VALUES (30, '123456789', '11112222', '10001');
------------------------------------------------------------------
INSERT INTO empregado (idade, nif, nic, numero_interno)
    VALUES (28, '987654321', '33334444', '10002');
------------------------------------------------------------------
INSERT INTO cliente (nif)
    VALUES ('123456789');
------------------------------------------------------------------
INSERT INTO cliente (nif)
    VALUES ('987654321');
------------------------------------------------------------------
INSERT INTO equipamento (ean, numero_exemplar, estado, preco, data_na_loja)
    VALUES ('1112223334445', 1, 'BOM', 750, DATE '2024-10-15');
------------------------------------------------------------------
INSERT INTO equipamento (ean, numero_exemplar, estado, preco, data_na_loja)
    VALUES ('2223334445556', 2, 'MAU', 500, DATE '2024-01-15');
------------------------------------------------------------------
INSERT INTO fatura (nipc, numero_sequencial, data_fatura)
    VALUES (123456789, 1, DATE '2024-06-11');
------------------------------------------------------------------
INSERT INTO fatura (nipc, numero_sequencial, data_fatura)
    VALUES (987654321, 2, DATE '2024-01-10');
------------------------------------------------------------------
COMMIT;
------------------------------------------------------------------