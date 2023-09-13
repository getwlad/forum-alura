
CREATE TABLE IF NOT EXISTS cursos (
                                                         `id` INT NOT NULL AUTO_INCREMENT,
                                                         `nome` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
