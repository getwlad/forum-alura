alter table usuarios add ativo tinyint;
update usuarios set ativo = true;
