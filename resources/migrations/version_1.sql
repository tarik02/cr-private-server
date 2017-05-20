DROP TABLE IF EXISTS `arenas`;
DROP TABLE IF EXISTS `cards`;
DROP TABLE IF EXISTS `chests`;
DROP TABLE IF EXISTS `home_chests`;
DROP TABLE IF EXISTS `levels`;
DROP TABLE IF EXISTS `players`;
DROP TABLE IF EXISTS `server`;
DROP TABLE IF EXISTS `players_cards`;
DROP TABLE IF EXISTS `decks`;

CREATE TABLE `server` (
	`version` INTEGER NOT NULL DEFAULT 0
);

INSERT INTO `server`(`version`) VALUES(1);


CREATE TABLE `players` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	VARCHAR(32) NOT NULL,
	`gold`	INTEGER NOT NULL DEFAULT 0,
	`gems`	INTEGER NOT NULL DEFAULT 0,
	`level_id`	INTEGER NOT NULL DEFAULT 0,
	`level_experience`	INTEGER NOT NULL DEFAULT 0,
	`arena_id`	INTEGER NOT NULL DEFAULT 0,
	`arena_trophies`	INTEGER NOT NULL DEFAULT 0,
	`favourite_card_id`	INTEGER NOT NULL DEFAULT 0,
	`wins`	INTEGER NOT NULL DEFAULT 0,
	`looses`	INTEGER NOT NULL DEFAULT 0,
	`treee_crowns_wins`	INTEGER NOT NULL DEFAULT 0,
	`clan_id`	INTEGER,
	`clan_role`	INTEGER
);

CREATE TABLE `arenas` (
	`id`	INTEGER NOT NULL,
	`name`	VARCHAR(32) NOT NULL UNIQUE,
	`trophies`	INTEGER NOT NULL UNIQUE,
	`is_league`	BOOLEAN NOT NULL,
	PRIMARY KEY(`id`)
);

CREATE TABLE `levels` (
	`id`	INTEGER NOT NULL,
	`experience`	INTEGER NOT NULL UNIQUE,
	PRIMARY KEY(`id`)
);

CREATE TABLE `home_chests` (
	`player_id`	INTEGER NOT NULL,
	`slot`	INTEGER(1) NOT NULL,
	`chest_id`	INTEGER NOT NULL,
	`open_start`	INTEGER,
	`open_end`	INTEGER,
	`status`	INTEGER(1) NOT NULL,
	UNIQUE(player_id, slot),
	FOREIGN KEY(`player_id`) REFERENCES `players`(`id`),
	FOREIGN KEY(`chest_id`) REFERENCES `chests`(`id`)
);

CREATE TABLE `chests` (
	`id` INTEGER NOT NULL,
	`arena_id` INTEGER NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`arena_id`) REFERENCES `arenas`(`id`)
);

CREATE TABLE `cards` (
	`id`	INTEGER NOT NULL,
	`name`	VARCHAR(32) NOT NULL UNIQUE,
	`type`	INTEGER NOT NULL,
	PRIMARY KEY(`id`)
);

CREATE TABLE `players_cards` (
	`player_id`	INTEGER NOT NULL,
	`card_id`	INTEGER NOT NULL,
	`level`	INTEGER NOT NULL DEFAULT 0,
	`count`	INTEGER NOT NULL DEFAULT 0,
	`status`	INTEGER(1) NOT NULL DEFAULT 0,
	UNIQUE(`player_id`, `card_id`),
	FOREIGN KEY(`card_id`) REFERENCES `cards`(`id`),
	FOREIGN KEY(`player_id`) REFERENCES `players`(`id`)
);

CREATE TABLE `decks` (
	`player_id`	INTEGER NOT NULL,
	`slot`	INTEGER(1) NOT NULL,
	`is_current`	BOOLEAN NOT NULL,
	`card1_id`	INTEGER,
	`card2_id`	INTEGER,
	`card3_id`	INTEGER,
	`card4_id`	INTEGER,
	`card5_id`	INTEGER,
	`card6_id`	INTEGER,
	`card7_id`	INTEGER,
	`card8_id`	INTEGER,
	FOREIGN KEY(`card1_id`) REFERENCES `cards`(`id`),
	FOREIGN KEY(`card2_id`) REFERENCES `cards`(`id`),
	FOREIGN KEY(`card3_id`) REFERENCES `cards`(`id`),
	FOREIGN KEY(`card4_id`) REFERENCES `cards`(`id`),
	FOREIGN KEY(`card5_id`) REFERENCES `cards`(`id`),
	FOREIGN KEY(`card6_id`) REFERENCES `cards`(`id`),
	FOREIGN KEY(`card7_id`) REFERENCES `cards`(`id`),
	FOREIGN KEY(`card8_id`) REFERENCES `cards`(`id`)
);

