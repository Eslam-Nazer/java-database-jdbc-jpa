DROP TABLE IF EXISTS "books";
DROP TABLE IF EXISTS "auhtors";

-- CREATE SEQUENCE author_id_seq OWNED BY "authors"."id";

CREATE TABLE "authors" (
    "id" BIGSERIAL NOT NULL,
    "name" text,
    "age"  INTEGER,
    CONSTRAINT "authors_pkey" PRIMARY KEY ("id")
);

CREATE TABLE "books" (
    "isbn" text NOT NULL,
    "title" text NOT NULL,
    "author_id" bigint,
    CONSTRAINT "book_pkey" PRIMARY KEY ("isbn"),
    CONSTRAINT "fk_author" FOREIGN KEY ("author_id")
    REFERENCES "authors"("id")
);