ALTER TABLE organization ADD COLUMN contractingorganization boolean;
ALTER TABLE organization ALTER COLUMN contractingorganization SET DEFAULT false;