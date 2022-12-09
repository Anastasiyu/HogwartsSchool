
-- liquibase formatted sql

-- changeset safiulina:1

CREATE INDEX idx_student_name ON student (name);

-- changeset safiulina:2


CREATE UNIQUE INDEX idx_student_name_color ON faculty (name, color);