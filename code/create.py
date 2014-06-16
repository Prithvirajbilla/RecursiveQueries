sql = """
drop table A%d cascade constraints;
drop table b%d cascade constraints;
drop table c%d cascade constraints;

create table A%d
(
	"A_ID" NUMBER PRIMARY KEY,
	"A_NAME" VARCHAR(20 BYTE),
	"A_DETAILS" VARCHAR(20 BYTE),
	"C_KEY" NUMBER
);
create table B%d
(
		"B_ID" NUMBER PRIMARY KEY,
	"B_NAME" VARCHAR(20 BYTE),
	"B_DETAILS" VARCHAR(20 BYTE),
	"A_KEY" NUMBER REFERENCES A%d("A_ID")
);

create table C%d
(
		"C_ID" NUMBER PRIMARY KEY,
	"C_NAME" VARCHAR(20 BYTE),
	"C_DETAILS" VARCHAR(20 BYTE),
	"B_KEY" NUMBER REFERENCES B%d("B_ID")
);

ALTER TABLE "A%d" ADD CONSTRAINT "A%d_FK1" FOREIGN KEY ("C_KEY") REFERENCES "C%d" ("C_ID");"""
l = input()
k = []
for i in range(11):
	k.append(l)
print sql%tuple(k)
