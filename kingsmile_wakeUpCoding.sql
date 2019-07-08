alter session set "_oracle_script"=true;
create user kingsmile identified by oracle account unlock;
grant connect, resource to kingsmile;
grant create table to kingsmile;
alter user kingsmile default tablespace users quota unlimited on users;

-- 테이블 생성
create table userdb(
    id varchar2(20) constraint userdb1_id_pk primary key,
    pwd varchar2(20) not null,
    nick varchar2(18) not null
);