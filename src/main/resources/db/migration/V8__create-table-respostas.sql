CREATE TABLE respostas
(
    `id`            INT          NOT NULL AUTO_INCREMENT,
    `mensagem`      VARCHAR(255) NOT NULL,
    `data_mensagem` DATE         NOT NULL,
    `usuarios_id`   INT          NOT NULL,
    `topicos_id`    INT          NOT NULL,

    PRIMARY KEY (`id`),
    INDEX           `fk_Respostas_usuarios1_idx` (`usuarios_id` ASC) VISIBLE,
    INDEX           `fk_Respostas_topicos1_idx` (`topicos_id` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    CONSTRAINT `fk_Respostas_usuarios1`
        FOREIGN KEY (`usuarios_id`)
            REFERENCES `usuarios` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_Respostas_topicos1`
        FOREIGN KEY (`topicos_id`)
            REFERENCES `topicos` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION

);