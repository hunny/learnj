-- CREATE DATABASE qichacha;
-- DROP TABLE company;
CREATE TABLE IF NOT EXISTS company (
  id INT NOT NULL AUTO_INCREMENT, /* ID */
  name VARCHAR(254) NOT NULL, /* 企业名称 */
  code VARCHAR(254), /* 海关经营编码 */
  city VARCHAR(1024), /* 地市*/
  outAmount DECIMAL(20,4) DEFAULT 0, /* 出口总额（累积）美金*/
  sign VARCHAR(1), /* 是否签约 */
  businesser VARCHAR(254), /* 企业法人 */
  mobile VARCHAR(254), /* 联系电话 */
  address VARCHAR(254), /* 企业地址 */
  orgCode VARCHAR(254), /* 组织机构代码 */
  socialCode VARCHAR(254), /* 统一社会信誉代码 */
  registerNumber VARCHAR(254), /* 注册号 */
  signAmount VARCHAR(254), /* 注册资本 */
  companyType VARCHAR(254), /* 公司类型 */
  openDate VARCHAR(254), /* 成立日期 */
  openLimit VARCHAR(254), /* 营业期限 */
  signAddress VARCHAR(254), /* 登记机关 */
  companySize VARCHAR(254), /* 公司规模 */
  industry VARCHAR(254), /* 所属行业 */
  englishName VARCHAR(254), /* 英文名 */
  usedName VARCHAR(254), /* 曾用名 */
  businessScope VARCHAR(254), /* 经营范围 */
  mobile VARCHAR(254), /* 客户状态 */
  saleman VARCHAR(254), /* 销售代表 */
  serviceman VARCHAR(254), /* 服务代表 */
  resourcedb VARCHAR(254), /* 客户所在资源库 */
  lastUpdated DATETIME, /* 创建时间 */
  dateCreated DATETIME, /* 创建时间 */
  INDEX index_1(name),
  UNIQUE KEY uk_1(name),
  UNIQUE KEY uk_2(code),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- DROP TABLE proxy;
CREATE TABLE IF NOT EXISTS proxy (
  id INT NOT NULL AUTO_INCREMENT, /* ID */
  ip VARCHAR(254) NOT NULL, /* IP */
  port VARCHAR(10), /* 端口 */
  timeout VARCHAR(10), /* 时长 */
  remark VARCHAR(254), /* 备注 */
  lastUpdated DATETIME, /* 创建时间 */
  dateCreated DATETIME, /* 创建时间 */
  INDEX index_1(ip),
  UNIQUE KEY uk_1(ip, port),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;