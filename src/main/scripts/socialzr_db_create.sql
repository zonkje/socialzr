create table comment (id bigint not null auto_increment, create_date datetime, last_modified_date datetime, text varchar(255), author_id bigint not null, post_id bigint not null, primary key (id)) engine=MyISAM;
create table comment_thumb_up (id bigint not null auto_increment, create_date datetime, last_modified_date datetime, author_id bigint, comment_id bigint, primary key (id)) engine=MyISAM;
create table contact_information (id bigint not null auto_increment, create_date datetime, last_modified_date datetime, address varchar(255), city varchar(255), country varchar(255), state varchar(255), zip_code varchar(255), email varchar(255), phone_number varchar(255), primary key (id)) engine=MyISAM;
create table contact_information_websitesurls (contact_information_id bigint not null, websitesurls varchar(255)) engine=MyISAM;
create table post (id bigint not null auto_increment, create_date datetime, last_modified_date datetime, text varchar(255), author_id bigint not null, primary key (id)) engine=MyISAM;
create table post_label (id bigint not null auto_increment, create_date datetime, last_modified_date datetime, name varchar(255), primary key (id)) engine=MyISAM;
create table post_post_label (post_label_id bigint not null, post_id bigint not null) engine=MyISAM;
create table post_thumb_up (id bigint not null auto_increment, create_date datetime, last_modified_date datetime, author_id bigint, post_id bigint, primary key (id)) engine=MyISAM;
create table social_group (id bigint not null auto_increment, create_date datetime, last_modified_date datetime, access_level varchar(255) not null, avatar_url varchar(255), description varchar(255), name varchar(48), creator_id bigint not null, primary key (id)) engine=MyISAM;
create table social_group_post (id bigint not null, social_group_id bigint not null, primary key (id)) engine=MyISAM;
create table user (id bigint not null auto_increment, create_date datetime, last_modified_date datetime, avatar_url varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255) not null, role varchar(255) not null, username varchar(255) not null, contact_information_id bigint, primary key (id)) engine=MyISAM;
create table user_social_group (social_group_id bigint not null, user_id bigint not null) engine=MyISAM;
create table violation_report (id bigint not null auto_increment, create_date datetime, last_modified_date datetime, text varchar(255), author_id bigint not null, reported_user_id bigint not null, primary key (id)) engine=MyISAM;
alter table comment add constraint FKh1gtv412u19wcbx22177xbkjp foreign key (author_id) references user (id);
alter table comment add constraint FKs1slvnkuemjsq2kj4h3vhx7i1 foreign key (post_id) references post (id);
alter table comment_thumb_up add constraint FK2xmmk5tg5t1wf3yx32kiid5db foreign key (author_id) references user (id);
alter table comment_thumb_up add constraint FK5059jn1dwjtilku1uk8b1hna5 foreign key (comment_id) references comment (id);
alter table contact_information_websitesurls add constraint FKqmnk98epe6xyvbqhx8wnghkac foreign key (contact_information_id) references contact_information (id);
alter table post add constraint FK12njtf8e0jmyb45lqfpt6ad89 foreign key (author_id) references user (id);
alter table post_post_label add constraint FK2crfp7hmlo5i6rrkleqvg2wt8 foreign key (post_id) references post (id);
alter table post_post_label add constraint FK9ywbr9m1ts470cv290aifxq2v foreign key (post_label_id) references post_label (id);
alter table post_thumb_up add constraint FKbnvwi7tm89yv8a4rr74s1bao7 foreign key (author_id) references user (id);
alter table post_thumb_up add constraint FK5gyew9at0b9kmvf7u2avpscx3 foreign key (post_id) references post (id);
alter table social_group add constraint FKe5ffhh7emtqqret2ih8a11ac1 foreign key (creator_id) references user (id);
alter table social_group_post add constraint FKln10phd13owecoitcvklatl8x foreign key (social_group_id) references social_group (id);
alter table social_group_post add constraint FKmerulv5h6w6n0p83h2rb2aplg foreign key (id) references post (id);
alter table user add constraint FKd6n162q6610mf20yxqyk8i770 foreign key (contact_information_id) references contact_information (id);
alter table user_social_group add constraint FKctsr7e96wagidmivc1ep5ilme foreign key (user_id) references user (id);
alter table user_social_group add constraint FK1lbb9idihcfnv6ybvkeiwy26o foreign key (social_group_id) references social_group (id);
alter table violation_report add constraint FK7tal0xejrg58g4e6b9cfcurof foreign key (author_id) references user (id);
alter table violation_report add constraint FKpjo215hx9qxlfiecmnvv7odwf foreign key (reported_user_id) references user (id);
