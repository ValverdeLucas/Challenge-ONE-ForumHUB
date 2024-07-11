create table topicos(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensagem varchar(2500) not null,
    data datetime not null,
    estado_topico varchar(100) not null,
    autor varchar(100) not null,
    curso varchar(100) not null,

    primary key(id)

);