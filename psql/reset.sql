-- Check the current database name
SELECT current_database() INTO current_db;

-- Drop all tables in the 'bookshare' database if connected to 'bookshare'
DO $$DECLARE
    r record;
BEGIN
    IF current_db = 'bookshare' THEN
        FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
            EXECUTE 'DROP TABLE IF EXISTS ' || r.tablename || ' CASCADE';
        END LOOP;
    ELSE
        RAISE NOTICE 'Not connected to the "bookshare" database. No action taken.';
    END IF;

    -- List of SQL files to execute
    \set sql_files 'users.sql books.sql transactions.sql login_events.sql'

    -- Loop through the list of SQL files and execute them
    FOR file IN SELECT unnest(string_to_array(:'sql_files', ' ')) LOOP
        RAISE NOTICE 'Executing file: %', file;
        \i :file;
    END LOOP;

END$$;

