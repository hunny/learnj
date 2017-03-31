ALTER TABLE proxy ADD COLUMN validate VARCHAR(2) NOT NULL DEFAULT 'N'; /* 是否有效Y/N */
ALTER TABLE proxy ADD COLUMN lastUpdated DATETIME; /* 更新时间 */