CREATE OR REPLACE FUNCTION apply_to_all_tenants(p_table text, p_alter_sql text)
RETURNS void LANGUAGE plpgsql AS $$
DECLARE
r record;
BEGIN
FOR r IN
SELECT n.nspname AS schema_name
FROM pg_class c
         JOIN pg_namespace n ON n.oid = c.relnamespace
WHERE c.relkind = 'r'
  AND c.relname = p_table
  AND n.nspname LIKE 'r100\_%' ESCAPE '\'
    LOOP
    EXECUTE format('ALTER TABLE %I.%I %s;', r.schema_name, p_table, p_alter_sql);
RAISE NOTICE 'Applied on %.%: %', r.schema_name, p_table, p_alter_sql;
END LOOP;
END $$;


SELECT apply_to_all_tenants('goods', 'ADD COLUMN IF NOT EXISTS price numeric(12,2) DEFAULT 0 NOT NULL');
