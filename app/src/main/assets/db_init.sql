PRAGMA foreign_keys = ON;

CREATE TABLE "purchases" (
  "id"			INTEGER PRIMARY KEY,
  "name"		TEXT NOT NULL UNIQUE COLLATE NOCASE,
  "count" 	    INTEGER NOT NULL,
  "enabled" 	INTEGER NOT NULL
);


INSERT INTO "purchases" ("name", "count", "enabled")
VALUES
  ("Milk", 5,0),
  ("Broad", 2,1),
  ("Milt", 3,0),
  ("Car", 1,0),
  ("Paper", 10,1);
