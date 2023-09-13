
CREATE TABLE IF NOT EXISTS  usuarios (
                                                           `id` INT NOT NULL AUTO_INCREMENT,
                                                           `login` VARCHAR(45) NOT NULL,
    `senha` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);