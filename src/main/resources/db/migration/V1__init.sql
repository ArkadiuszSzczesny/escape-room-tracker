CREATE TABLE voivodeships (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE cities (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    voivodeships_id UUID NOT NULL REFERENCES voivodeships(id)
);

CREATE TABLE companies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    website VARCHAR(255)
);

CREATE TABLE branches (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id UUID NOT NULL REFERENCES companies(id),
    city_id UUID NOT NULL REFERENCES cities(id),
    address VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id UUID NOT NULL REFERENCES roles(id)
);

CREATE TABLE genres (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE escape_room (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    difficulty SMALLINT NOT NULL CHECK (difficulty BETWEEN 1 AND 5),
    duration_minutes INT NOT NULL,
    branch_id UUID NOT NULL REFERENCES branches(id)
);

CREATE TABLE escape_room_genres (
    escape_room_id UUID NOT NULL REFERENCES escape_room(id),
    genre_id UUID NOT NULL REFERENCES genres(id),
    PRIMARY KEY (escape_room_id, genre_id)
);

CREATE TABLE visits (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id),
    escape_room_id UUID NOT NULL REFERENCES escape_room(id),
    played_at DATE NOT NULL,
    is_escaped BOOLEAN NOT NULL,
    escape_time_sec INT
);

CREATE TABLE visit_participants (
    id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    visit_id UUID NOT NULL REFERENCES visits(id) ON DELETE CASCADE,
    companion_user_id UUID REFERENCES users(id),
    unregistered_name VARCHAR(100),

    CONSTRAINT chk_participant CHECK (
        (companion_user_id IS NOT NULL AND unregistered_name IS NULL) OR
        (companion_user_id IS NULL AND unregistered_name IS NOT NULL)
        )
);

INSERT INTO voivodeships (name) VALUES
    ('Dolnośląskie'),
    ('Kujawsko-Pomorskie'),
    ('Lubelskie'),
    ('Lubuskie'),
    ('Łódzkie'),
    ('Małopolskie'),
    ('Mazowieckie'),
    ('Opolskie'),
    ('Podkarpackie'),
    ('Podlaskie'),
    ('Pomorskie'),
    ('Śląskie'),
    ('Świętokrzyskie'),
    ('Warmińsko-Mazurskie'),
    ('Wielkopolskie'),
    ('Zachodniopomorskie');

INSERT INTO roles (name) VALUES
    ('ROLE_USER'),
    ('ROLE_MODERATOR'),
    ('ROLE_ADMIN');

INSERT INTO genres (name) VALUES
    ('Horror'),
    ('Sci-Fi'),
    ('Familijny'),
    ('Detektywistyczny'),
    ('Przygodowy'),
    ('Historyczny'),
    ('Fantasy');