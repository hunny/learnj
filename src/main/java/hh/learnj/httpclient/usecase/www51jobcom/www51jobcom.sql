-- CREATE DATABASE w51job;
-- DROP TABLE company;
CREATE TABLE IF NOT EXISTS company (
  id INT NOT NULL AUTO_INCREMENT, /* ID */
  name VARCHAR(254) NOT NULL, /* 企业名称 */
  url51 VARCHAR(1024), /* 51url*/
  url VARCHAR(1024), /* 企业url*/
  address VARCHAR(1024), /* 企业地址 */
  details TEXT, /* 企业详情 */
  dateCreated DATETIME, /* 创建时间 */
  INDEX index_1(name),
  UNIQUE KEY uk_1(url51),
  UNIQUE KEY uk_2(url),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;