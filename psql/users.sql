--
-- PostgreSQL database dump
--

-- Dumped from database version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: aaron
--

CREATE TABLE public.users (
    id integer NOT NULL,
    nickname character varying(10),
    email character varying NOT NULL,
    hashed_password character varying NOT NULL,
    district character varying(20),
    credits integer DEFAULT 6,
    date_registered timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    consent_to_share boolean DEFAULT false,
    is_admin boolean DEFAULT false
);


ALTER TABLE public.users OWNER TO aaron;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: aaron
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO aaron;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: aaron
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: aaron
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: aaron
--

COPY public.users (id, nickname, email, hashed_password, district, credits, date_registered, consent_to_share, is_admin) FROM stdin;
1	user1	user1@example.com	$2a$10$SOMEHASH...	District 1	10	2023-01-01 10:00:00	t	t
2	user2	user2@example.com	$2a$10$OTHERHASH...	District 2	5	2023-01-02 14:30:00	f	f
3	user3	user3@example.com	$2a$10$ANOTHERHASH...	District 1	15	2023-01-03 16:45:00	t	f
4	\N	mail@example.com	$2a$10$mXsvgjvocMcUZ0p4BjbpQuhgHAguUeTBL/6VANlEd/X2rmUxQiMxq	\N	6	2023-10-08 23:05:14.955737	f	f
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: aaron
--

SELECT pg_catalog.setval('public.users_id_seq', 4, true);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: aaron
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: aaron
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

