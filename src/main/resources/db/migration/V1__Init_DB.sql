 create table post (
        id bigint not null auto_increment,
        anons varchar(255),
        date varchar(255),
        full_text varchar(255),
        title varchar(255),
        user_id bigint,
        primary key (id)
    );

 create table question (
        id bigint not null auto_increment,
        email varchar(255),
        name varchar(255),
        text_question varchar(255),
        primary key (id)
    );

 create table user (
        id bigint not null,
        password varchar(80),
        username varchar(30),
        primary key (id)
    );

 create table user_post_set (
        user_id bigint not null,
        post_set_id bigint not null,
        primary key (user_id, post_set_id)
    );

    alter table user_post_set
       add constraint UK_3g7oldka3d4uqtuqgrwj7int1
       unique (post_set_id);

    alter table post
       add constraint FK72mt33dhhs48hf9gcqrq4fxte
       foreign key (user_id)
       references user (id);

    alter table user_post_set
       add constraint FKlwxtwuc4d0itga8dg2sa6ipfp
       foreign key (post_set_id)
       references post (id);

    alter table user_post_set
       add constraint FKf7njxx4gty0fhi7em6l8aw58e
       foreign key (user_id)
       references user (id);