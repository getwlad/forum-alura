CREATE TABLE IF NOT EXISTS `db_forum_alura`.`topicos` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `titulo` VARCHAR(45) NOT NULL,
    `mensagem` VARCHAR(45) NOT NULL,
    `data_criacao` DATE NOT NULL,
    `status` ENUM("ABERTO", "FECHADO") NOT NULL,
    `curso` VARCHAR(45) NOT NULL,
    `autor` VARCHAR(45) NOT NULL,
    `usuarios_id` INT NOT NULL,
    `cursos_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    INDEX `fk_topicos_usuarios1_idx` (`usuarios_id` ASC) VISIBLE,
    INDEX `fk_topicos_cursos1_idx` (`cursos_id` ASC) VISIBLE,
    CONSTRAINT `fk_topicos_usuarios1`
    FOREIGN KEY (`usuarios_id`)
    REFERENCES `db_forum_alura`.`usuarios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_topicos_cursos1`
    FOREIGN KEY (`cursos_id`)
    REFERENCES `db_forum_alura`.`cursos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);