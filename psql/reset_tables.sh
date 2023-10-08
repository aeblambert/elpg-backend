#!/bin/bash

# List of SQL files to execute
SQL_FILES=("users.sql" "books.sql" "transactions.sql" "login_events.sql")

# PostgreSQL connection parameters
DB_USER="aaron"
DB_NAME="bookshare"

# SQL script to drop all tables in the 'bookshare' database
DROP_TABLES_SQL="DROP SCHEMA public CASCADE; CREATE SCHEMA public;"

# Execute the SQL script to drop tables
echo "Dropping all tables in the 'bookshare' database..."
echo "$DROP_TABLES_SQL" | psql -U "$DB_USER" -d "$DB_NAME"

# Execute the SQL files
for FILE in "${SQL_FILES[@]}"; do
    echo "Executing file: $FILE"
    psql -U "$DB_USER" -d "$DB_NAME" -a -f "$FILE"
done
