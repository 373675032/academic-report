/*
 * ==========================================================================
 * 郑重说明：本项目免费开源！原创作者为：薛伟同学，严禁私自出售。
 * ==========================================================================
 * B站账号：薛伟同学
 * 微信公众号：薛伟同学
 * 作者博客：http://xuewei.world
 * ==========================================================================
 * 陆陆续续总会收到粉丝的提醒，总会有些人为了赚取利益倒卖我的开源项目。
 * 不乏有粉丝朋友出现钱付过去，那边只把代码发给他就跑路的，最后还是根据线索找到我。。
 * 希望各位朋友擦亮慧眼，谨防上当受骗！
 * ==========================================================================
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员编号',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin');

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '预约ID',
  `meeting_id` int(111) NULL DEFAULT NULL COMMENT '会议ID',
  `student_id` int(8) NULL DEFAULT NULL COMMENT '学生ID',
  `appointment_time` datetime(0) NULL DEFAULT NULL COMMENT '预约时间',
  `present` int(11) NULL DEFAULT NULL COMMENT '是否到场【1：是】【0：否】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES (13, 1, 1, '2023-12-21 14:33:10', 1);
INSERT INTO `appointment` VALUES (14, 1, 2, '2023-12-21 14:34:58', 1);
INSERT INTO `appointment` VALUES (15, 1, 6, '2023-12-21 14:35:15', 1);
INSERT INTO `appointment` VALUES (16, 1, 11, '2023-12-21 14:36:39', 0);
INSERT INTO `appointment` VALUES (17, 1, 18, '2023-12-21 14:36:54', 1);
INSERT INTO `appointment` VALUES (18, 4, 1, '2023-12-21 22:42:45', NULL);
INSERT INTO `appointment` VALUES (19, 3, 1, '2023-12-21 22:42:47', NULL);
INSERT INTO `appointment` VALUES (20, 3, 2, '2023-12-21 22:43:03', NULL);
INSERT INTO `appointment` VALUES (21, 4, 2, '2023-12-21 22:43:04', NULL);
INSERT INTO `appointment` VALUES (22, 3, 6, '2023-12-21 22:43:22', NULL);
INSERT INTO `appointment` VALUES (23, 4, 6, '2023-12-21 22:43:26', NULL);

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学院ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院名称',
  `leader_id` int(8) NULL DEFAULT NULL COMMENT '院长ID【对应职工表】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (1, '经济与管理学院', 6);
INSERT INTO `college` VALUES (2, '政法学院', 2);

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编号',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `leader_id` int(8) NULL DEFAULT NULL COMMENT '部门部长ID',
  `college_id` int(11) NULL DEFAULT NULL COMMENT '学院ID',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '101', '教务部', 1, 1, '101');
INSERT INTO `department` VALUES (2, '102', '宣传部', 2, 1, '102');
INSERT INTO `department` VALUES (3, '103', '教务部', 3, 2, '103');
INSERT INTO `department` VALUES (4, '104', '宣传部', 4, 2, '104');

-- ----------------------------
-- Table structure for meeting
-- ----------------------------
DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会议ID',
  `report_id` int(11) NULL DEFAULT NULL COMMENT '报告ID',
  `reporter_id` int(11) NULL DEFAULT NULL COMMENT '报告教师ID',
  `presenter_id` int(11) NULL DEFAULT NULL COMMENT '主持人教师ID',
  `report_time` datetime(0) NULL DEFAULT NULL COMMENT '报告时间',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告地点',
  `appointment_end` datetime(0) NULL DEFAULT NULL COMMENT '预约截止时间',
  `capacity` int(11) NULL DEFAULT NULL COMMENT '最大容纳人数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of meeting
-- ----------------------------
INSERT INTO `meeting` VALUES (1, 23, 1, 7, '2023-12-21 14:40:00', '主教学楼S1 506', '2023-12-21 14:40:00', 52);
INSERT INTO `meeting` VALUES (2, 24, 1, 2, '2023-12-21 20:00:00', '教学楼S6 802', '2023-12-21 19:00:00', 102);
INSERT INTO `meeting` VALUES (3, 21, 4, 5, '2023-12-21 23:00:00', '教学楼S1 208', '2023-12-21 23:00:00', 60);
INSERT INTO `meeting` VALUES (4, 25, 1, 5, '2023-12-22 10:30:00', '主教学楼S4 707', '2023-12-22 10:00:00', 80);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `to_teacher_id` int(11) NULL DEFAULT NULL COMMENT '消息教师ID',
  `message` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 4, '您的学术报告《审计服务重大风险防控的理论逻辑与关键研究问题》：已通过 学院院长 审核。审核意见：通过审核！', '2021-04-22 13:57:55');
INSERT INTO `message` VALUES (2, 4, '您的学术报告《审计服务重大风险防控的理论逻辑与关键研究问题》：已通过 教务部门 审核。审核意见：通过审核！', '2021-04-22 13:58:38');
INSERT INTO `message` VALUES (6, 1, '您的学术报告《探索未来科技新纪元：AIGC的革命性影响》：已通过 学院院长 审核。审核意见：通过审核！', '2023-12-21 17:56:56');
INSERT INTO `message` VALUES (7, 1, '您的学术报告《探索未来科技新纪元：AIGC的革命性影响》：已通过 教务部门 审核。审核意见：通过审核！', '2023-12-21 17:57:14');
INSERT INTO `message` VALUES (8, 1, '您的学术报告《探索未来科技新纪元：AIGC的革命性影响》：已经安排了学术报告会议。详情见 会议安排。', '2023-12-21 17:58:29');
INSERT INTO `message` VALUES (9, 4, '您的学术报告《审计服务重大风险防控的理论逻辑与关键研究问题》：已经安排了学术报告会议。详情见 会议安排。', '2023-12-21 18:22:29');
INSERT INTO `message` VALUES (10, 1, '您的学术报告《探索未来科技新纪元：AIGC的革命性影响》：已通过 学院院长 审核。审核意见：通过审核！', '2023-12-21 22:30:55');
INSERT INTO `message` VALUES (11, 1, '您的学术报告《探索未来科技新纪元：AIGC的革命性影响》：已通过 教务部门 审核。审核意见：通过审核！', '2023-12-21 22:38:30');
INSERT INTO `message` VALUES (12, 1, '您的学术报告《探索未来科技新纪元：AIGC的革命性影响》：已经安排了学术报告会议。详情见 会议安排。', '2023-12-21 22:41:10');

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告名称',
  `reporter_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报告人教师ID',
  `reporter_info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '报告人简介',
  `info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '报告简介',
  `report_file` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学术报告文件',
  `attachment` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件文件存放路径',
  `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `status` int(11) NULL DEFAULT NULL COMMENT '【-2：教务部门审核未通过】【-1：院长审核未通过】【0：等待审核】【1：院长审核通过等待教务部门审核】【2：教务部门审核通过】【3：预约会议】【4：会议完成】【5：回收站】',
  `check_info1` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '-' COMMENT '院长审核不通过的指导意见',
  `check_info2` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教务部门审核不通过的指导意见',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES (4, '生物计算及其在生物学等领域的应用', '1001', '测试3', '测试3', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/e964137c40b840b7b39979453d45b4fd.zip', '2020-12-27 16:00:23', 0, '-', '???????????????');
INSERT INTO `report` VALUES (6, '量子游走搜索算法及例外格局', '1001', '测试5', '测试5', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/02e43ccfe32741fd8827fb59da22e02a.zip', '2020-12-27 15:59:46', 0, '-', '-');
INSERT INTO `report` VALUES (7, '一种高精度蛋白结构从头折叠方法tFold', '1001', '测试六', '测试六', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/7c85d7cab6674b1cb75409d47d3244bb.zip', '2020-12-27 15:59:32', 0, '-', '材料不齐');
INSERT INTO `report` VALUES (8, '机器智能时代——量化投资以及中美市场比较', '1001', '机器智能时代——量化投资以及中美市场比较', '机器智能时代——量化投资以及中美市场比较', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/740f6259855d475eb795c1426927fc2f.zip', '2020-12-27 16:01:20', 0, '-', '-');
INSERT INTO `report` VALUES (9, '激光悬浮微纳粒子及量子自旋光力学', '1001', '激光悬浮微纳粒子及量子自旋光力学', '激光悬浮微纳粒子及量子自旋光力学', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/0673c8edb0ea4f5fb671912cac5ad6af.zip', '2020-12-27 16:01:38', 0, '予以通过！', '-');
INSERT INTO `report` VALUES (10, '量化投资相关技术在普惠金融服务中的应用', '1001', '量化投资相关技术在普惠金融服务中的应用', '量化投资相关技术在普惠金融服务中的应用', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/dc79d7c250614514ba1a5ae34e3d8c52.zip', '2020-12-27 16:01:56', 0, '予以通过！', '教务部门审核通过！');
INSERT INTO `report` VALUES (11, '波-粒延迟选择实验', '1001', '波-粒延迟选择实验', '波-粒延迟选择实验', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/21e74c81b4d044a291f7796427b31b52.zip', '2020-12-27 16:02:16', 0, '这个实验非常具有研究价值！', '???');
INSERT INTO `report` VALUES (12, 'Development of navigation techniques in virtual reality: towards customized interaction', '1003', 'Yuyang Wang is a senior PhD candidate at the Arts et Metiers Institute of Technology, France, with a specialty in the field of virtual reality. His research interests include the application of data-driven modeling methods to develop intelligent and adaptive navigation and interaction systems in 3D virtual environments to provide a better user experience. His work in virtual reality research has been published in several top journals and conferences such as International Journal of Human-Computer Studies (CCF-A), IEEE VR, IEEE ITSC. Moreover, he has served as reviewer for IEEE Transactions on Visualization and Computer Graphics, and reviewer for several top conferences including IEEE VR, IEEE ISMAR, ACM VRST etc.', 'User-friendly virtual reality (VR) gained popularity in the last years thanks to the development of affordable head-mounted displays such as HTC Vive and Oculus Rift. The easy access to these devices enables researchers and engineers to develop various VR applications for medical training, education, or patient rehabilitation. Navigation plays an important role in a large number of domains using VR. Users are requested to customize their viewpoint to explore unknown virtual environments, searching or maneuver objects. However, cybersickness as an inherent issue of VR navigation becomes even worse. It is associated with various symptoms such as nausea, disorientation, headaches, fatigue, eye strain et al., posing a threat to user experience.\r\nMultiple human-related factors such as sex, age, gaming experience and ethnicity, and external factors including navigation methods or visual display parameters have proved to affect the occurrence of cybersickness. Therefore, the level of sickness symptoms varies significantly among individuals, depending on their characteristics, the technologies being utilized, the tasks being conducted and the design of the virtual environment. In this talk, I will present my work related to the evaluation and reduction of cybersickness, and discuss recent research aiming at designing the customized VR experience.', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/张珊珊/53ecc989b6cb42879e13406138c31dd1.zip', '2021-01-07 22:07:10', 0, '-', NULL);
INSERT INTO `report` VALUES (13, '科学基金改革与管理科学部“十四五”发展战略', '1003', '霍红，国家自然科学基金委员会管理科学部一处处长。', '报告将主要介绍国家自然科学基金委员会科学基金的改革措施，介绍管理科学部“十四五”管理科学的发展战略，对管理科学与工程（G01）学科内涵界定、学科发展规律、学科顶层设计与发展目标、学科布局优化、优先资助领域等方面的最新政策进行解读，并对2020年管理科学与工程学科自然科学基金项目的申请和资助情况进行总结与分析。', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/张珊珊/cf4aef8070f44be0bd93afbfa3c01808.zip', '2021-01-07 22:08:41', 0, '-', NULL);
INSERT INTO `report` VALUES (14, '解码MBA职业规划与转型', '1003', '李建华，曾任全球500强跨国企业人力资源总监，现任杭州微秦科技有限公司总经理。先后获得人力资源管理师、人力资源测评师职称，荣获第四届中国长三角十佳HR，曾担任合肥市高新区人力资源协会会长，安徽省人力资源经理人协会专家委员会成员，合肥市人社局HR专委会成员，合肥工业大学第六届MBA联合会副主席等荣誉。', '解密职场致胜之道，把握人生机遇，与不同的人交往，活出不同的人生，主要从以下三个方面与同学们进行交流:1、个人职业转型经历交流；2、个人MBA职业规划；3、现场与同学交流互动。', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/张珊珊/2604699514654719b8a33ff76333be5a.zip', '2021-01-07 22:09:49', 0, '解密职场致胜之道，把握人生机遇，与不同的人交往，活出不同的人生！', '通过审核！');
INSERT INTO `report` VALUES (15, '金融大数据分析与应用', '1003', '许伟，教授，博士生导师，长期从事金融科技、商业分析、信息系统与大数据应用等研究工作。中国人民大学杰出学者，信息技术中心副主任，信息学院经济信息管理系主任、信息系统与大数据应用实验室主任。《系统工程学报》编委，Internet Research、《系统工程理论与实践》等国内外期刊客座主编。主持国家自然科学基金等国家级项目多项，在Production and Operations Research、European Journal of Operational Research、Decision Support Systems、IEEE Trans.、ICIS、IJCAI等国内外期刊和国际会议上发表研究论文150余篇，出版专著5部，获得北京市科技新星、北京市优秀人才、北京市哲学社会科学优秀成果奖等多个奖项。', '随着大数据时代的到来，金融数据呈现出多模态、关系复杂、波动大等特征。如何利用大数据分析技术，对金融大数据进行关联分析和深度挖掘，应用到金融预测和智能决策场景中，是目前金融大数据分析的热点问题。本报告结合实际案例，阐述金融大数据在金融科技、金融市场等领域的典型应用。', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/张珊珊/e8bfff9d07524563917470e905282620.zip', '2021-01-07 22:11:42', 0, 'OK！', '通过审核！');
INSERT INTO `report` VALUES (16, '政务公开第三方评估', '1003', '肖卫兵，上海政法学院教授，经济法学院副院长。澳大利亚塔斯马尼亚大学法学博士，上海市浦江人才。主要研究领域为政府信息公开、数据开放、社会信用法制。参与过《政府信息公开条例》修改和《上海市社会信用条例》起草工作。在国内外出版过中英文专著4本，评估报告2本，发表期刊论文50多篇。创立“信息公开”微信公众号。', '政务公开第三方评估是新修订的《政府信息公开条例》第47条的明确要求，也是提升我国政务公开工作实效的重要举措。对于政务公开领域的第三方评估，现在不是“要不要”评的问题，而是“怎么评”、“评什么”的问题。这就需要总结现有国内外实践，围绕第三方评估地位、评估主体、评估层级、评估类型、评估方式、评估指标、评估方法、评估局限和结果使用进行系统总结评析，以此明确未来评估方向，进一步发挥第三方评估促公开、增实效作用。', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/张珊珊/fc94915557ee43a9af47ac37fe2b08b0.zip', '2021-01-07 22:12:43', 0, '非常适合学生学习', '通过审核！');
INSERT INTO `report` VALUES (17, '收购兼并之实战--以ESTUN为例', '1004', '袁琴，合肥工业大学2009级MBA校友，南京大学会计学硕士。现任南京埃斯顿自动化股份有限公司副总经理、财务总监兼董秘。曾获得新财富金牌董秘、新浪财经金牌董秘、金牛奖最佳董秘、新财富资本运作TOP10等称号。', '报告内容包括：\r\n（1）收购兼并的驱动因素、基本概念；\r\n（2）收购兼并的估值方法；\r\n（3）ESTUN国际化收购兼并的实践案例；\r\n（4）市场上收购兼并的失败案例；\r\n（5）收购兼并的经验总结。', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/李思思/e96f873c70f04701b1c436f39e5725f4.zip', '2021-01-07 22:14:12', 0, '不错！很有意义', NULL);
INSERT INTO `report` VALUES (18, '数据资产运营的信息价值评估', '1004', '林漳希教授，美国德克萨斯理工大学商学院终身教授、荣誉退休教授，合肥工业大学兼职教授，清华大学数据科学研究院经济金融数据研究中心副主任。同济大学经管学院、电信学院兼职教授，哈尔滨工业大学经管学院客座教授。林漳希教授的研究领域为电子商务信息经济、金融科技、数据挖掘应用和人工智能，曾发表学术论文200余篇，主持过国家级科研项目及阿里巴巴等大型企业委托课题20余项，曾获省部级科技进步二等奖1项、三等奖1项，目前并担任Decision Support Systems、Electronic Commerce Research学术期刊副主编和Journal of Database Management期刊的编委。', '数据作为珍贵的无形资产在大数据时代是企业运营的核心，如何评估数据资产的价值是企业各种决策中一个重要的问题。但是在大数据生态环境中数据作为信息的载体，在融合后讲产生信息倍增效应，其实际运营价值是数据价值分析常用评估方法，即成本法、收益法和市场法无法处理的。这个讲座以商业银行为对象，从信息融合的问题本质入手，应用经济学方法，探索数据资产运营对企业价值的影响因素和评估方法，同时对新增信息的价值以及对外交流的信息成本做进一步分析。具体内容包括：（1）问题解析；\r\n（2）从数据融合到信息融合；\r\n（3）数据融合的信息价值模型；\r\n（4）数据融合参与方的博弈模型；\r\n（5）数据资产运营价值提升的实现。', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/李思思/7fc1edb7f7fe4ab380f1fc6204d36c8c.zip', '2021-01-07 22:15:31', 0, '数据作为珍贵的无形资产在大数据时代是企业运营的核心', NULL);
INSERT INTO `report` VALUES (19, 'AACSB认证2020新标准', '1004', '李天行，天主教辅仁大学商学研究所学术特聘教授兼国际与资源发展副校长，AACSB认证访视委员会(peer review team, PRT)评监委员，AACSB认证顾问(mentor)，AACSB 认证研讨会(business accreditation seminar, BAS)大中华区特约讲座，AACSB初次认证委员会(initial accreditation committee, IAC)专家委员。', '为确保大学商业教育的质量和持续改进，AACSB认证在2013版的标准上制定了2020版新标准，本次报告将围绕2020版新标准，论述后期需要提交的进展报告和最终版自评估报告需要注意的标准变化。', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/李思思/f58b2f2034ca4cb3bd2f27a5ec9ab63d.zip', '2021-01-07 22:16:23', 0, '为确保大学商业教育的质量和持续改进，AACSB认证在2013版的标准上制定了2020版新标准，本次报告将围绕2020版新标准。这篇报告做的非常详细。值得广大师生学习交流！', '很值得广大师生学习交流！');
INSERT INTO `report` VALUES (20, '数据资产运营的信息价值评估', '1004', '林漳希教授，美国德克萨斯理工大学商学院终身教授、荣誉退休教授，合肥工业大学兼职教授，清华大学数据科学研究院经济金融数据研究中心副主任。同济大学经管学院、电信学院兼职教授，哈尔滨工业大学经管学院客座教授。林漳希教授的研究领域为电子商务信息经济、金融科技、数据挖掘应用和人工智能，曾发表学术论文200余篇，主持过国家级科研项目及阿里巴巴等大型企业委托课题20余项，曾获省部级科技进步二等奖1项、三等奖1项，目前并担任Decision Support Systems、Electronic Commerce Research学术期刊副主编和Journal of Database Management期刊的编委。', '数据作为珍贵的无形资产在大数据时代是企业运营的核心，如何评估数据资产的价值是企业各种决策中一个重要的问题。但是在大数据生态环境中数据作为信息的载体，在融合后讲产生信息倍增效应，其实际运营价值是数据价值分析常用评估方法，即成本法、收益法和市场法无法处理的。这个讲座以商业银行为对象，从信息融合的问题本质入手，应用经济学方法，探索数据资产运营对企业价值的影响因素和评估方法，同时对新增信息的价值以及对外交流的信息成本做进一步分析。具体内容包括：（1）问题解析；（2）从数据融合到信息融合；（3）数据融合的信息价值模型；（4）数据融合参与方的博弈模型；（5）数据资产运营价值提升的实现。', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/李思思/05f2e5ca2f1b4f9190d26a1924b38c82.zip', '2021-01-07 22:17:18', 0, '数据作为珍贵的无形资产在大数据时代是企业运营的核心，如何评估数据资产的价值是企业各种决策中一个重要的问题。', NULL);
INSERT INTO `report` VALUES (21, '审计服务重大风险防控的理论逻辑与关键研究问题', '1004', '蔡春，西南财经大学二级教授、博士生导师，西南财经大学中国政府审计研究中心主任，四川省学术与技术带头人，四川省突出贡献专家。中国审计学会副会长，财政部会计名家。国家审计署国家审计准则咨询专家组专家，国务院学位委员会全国审计专业学位研究生教学指导委员会委员，中国会计学会审计专业委员会委员，中国重要审计学专家名录入选者。世界银行贷款资助项目首席专家、国家社科基金重大项目首席专家、教育部哲学社会科学重大课题攻关项目首席专家、国务院政府津贴专家，美国伊利诺大学国际会计教育与研究中心高级访问学者（1996～1997）。\r\n蔡春教授被学术界誉为“系统研究审计理论结构第一人”，曾获教育部霍英东奖励基金（经济学最高奖，1996），教育部国家级教学团队（会计学）负责人。在Managerial Auditing Journal、International Finance and Accounting、China Journal of Accounting Research和《经济研究》、《会计研究》、《经济学家》、《管理科学》、《审计研究》等期刊公开发表学术论文130余篇。担任《会计研究》、《审计研究》、《中国会计与财务研究》等期刊编委。', '围绕审计为何要关注风险防控，要防控哪些重大风险以及审计如何服务于重大风险防控三个关键研究问题，系统论述审计服务重大风险防控的理论逻辑。', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/acfca231f498449981da839a5371e7c2.pdf', 'https://su-share.oss-cn-beijing.aliyuncs.com/李思思/efc9ef96aa554e5092c6e3c05c09aa17.zip', '2021-01-07 22:18:23', 4, '通过审核！', '通过审核！');
INSERT INTO `report` VALUES (23, '揭秘Java线程池：深度解析并发编程利器', '1001', '薛伟，一位拥有丰富经验的Java后端工程师。在编程领域有着卓越的技能与深厚的知识积累，对于软件架构设计及数据库优化有独到见解。他致力于解决复杂的业务问题，并不断探索创新技术，以提高系统性能和用户体验。薛伟积极参与开源项目，贡献自己的力量。他的专业素养和执着追求使他在业界备受认可。', '摘要\r\n\r\n本报告旨在深入探讨Java线程池，作为并发编程的核心工具之一，它的重要性不容忽视。我们将揭示其内部工作机制，并详细分析其实现原理。我们还将介绍如何使用Java线程池来提高程序性能，并讨论在实际应用中可能遇到的问题及其解决策略。\r\n\r\n一、引言\r\n\r\n在多任务并行处理的背景下，线程池已成为Java程序员开发高效并发应用程序的重要工具。通过合理的管理和利用线程池资源，可以有效减少系统开销，提高程序运行效率。然而，对于开发者来说，理解和掌握Java线程池的工作机制并非易事。本文将深入剖析Java线程池的设计思想、工作原理以及实际应用中的相关问题，希望能为读者提供有益的参考。\r\n\r\n二、Java线程池的基本概念\r\n\r\nJava线程池是一种用于管理线程的工具，它可以动态创建和销毁线程，从而避免了频繁地创建和销毁线程所带来的系统开销。此外，线程池还提供了对线程的统一管理和调度功能，使得并发编程更加简单、灵活。\r\n\r\n三、Java线程池的工作原理\r\n\r\nJava线程池主要包括以下几个部分：\r\n\r\n1. 工作队列：线程池中的所有任务都会放入到一个工作队列中等待执行。工作队列通常采用FIFO（先进先出）或者LIFO（后进先出）的数据结构，以确保任务按照一定的顺序进行执行。\r\n2. 线程池核心线程：线程池会维护一定数量的核心线程，这些线程会在需要时被启动，负责处理工作队列中的任务。\r\n3. 线程池补充线程：当工作队列中的任务耗尽时，线程池会从空闲线程池中取出一些线程进行任务处理，直到满足当前的任务需求为止。\r\n\r\n四、Java线程池的应用与优化\r\n\r\n在实际应用中，我们需要根据系统的负载情况合理设置线程池的大小，以便达到最优的性能。例如，在高负载情况下，我们可以适当增加线程池的大小；而在低负载情况下，为了节省系统资源，我们应该尽量减少线程池的大小。\r\n\r\n五、结论\r\n\r\nJava线程池是并发编程的重要工具，能够有效地提高程序性能。通过对线程池工作原理的深入了解，我们可以更好地利用线程池，为我们的应用程序带来更高的效率和更好的用户体验。\r\n\r\n六、未来研究方向\r\n\r\n未来的研究可以进一步探索Java线程池的高级特性，如自适应线程池、可重用线程等，以及如何利用这些特性提高并发编程的效率。同时，还可以研究线程池在分布式环境下的优化策略，以满足更大规模并发处理的需求。\r\n\r\n七、参考资料\r\n\r\n[1] Java并发编程的艺术 (David Liu)\r\n[2] Java Concurrency in Practice (Brian Goetz, Tim Peierls, Joshua Bloch)\r\n[3] Effective Java (Joshua Bloch)', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/849936f474d04c919ab5d07e254af37e.doc', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/85be440be04a4cd98190b3498981de97.zip', '2023-12-21 13:19:09', 4, '通过审核！', '通过审核！');
INSERT INTO `report` VALUES (24, '探索未来科技新纪元：AIGC的革命性影响', '1001', '薛伟，一位热爱科研的专业人士。他拥有多年的研究经验，并且在多个领域有着深厚的理论基础。他对科研充满热情，致力于解决复杂的问题，并不断探索新的研究方向。他的研究成果曾多次发表在国内外顶级期刊上，得到了同行的高度认可。薛伟积极参与各种学术活动，与同行交流思想，分享经验。他的学术态度严谨，工作认真负责，是一位值得信赖的合作伙伴。', '标题：探索未来科技新纪元：AIGC的革命性影响\r\n\r\n尊敬的学者们：\r\n\r\n我们非常荣幸地邀请您参加即将举行的“探索未来科技新纪元：AIGC的革命性影响”学术报告会议。此次会议将围绕人工智能生成内容（AIGC）这一前沿领域展开深入探讨，分享最新研究成果，展示其在科技创新和社会发展中的重要影响。\r\n\r\n近年来，随着计算机技术和机器学习的发展，AIGC已经取得了显著的进步。它涉及到自然语言处理、图像生成、音频合成等多个方面，正在改变我们获取信息、创作内容以及与世界互动的方式。在这个充满机遇和挑战的时代，我们需要更深入地理解AIGC的发展趋势，预测其可能带来的影响，并思考如何利用这些技术来推动科技进步和社会发展。\r\n\r\n本次会议将包括一系列主题演讲、小组讨论和技术展示环节，由来自全球顶尖大学、科研机构和企业的专家进行讲解和分享。他们将就以下几个方面展开讨论：\r\n\r\n1. AIGC的基本原理和发展历程；\r\n2. AIGC在各个领域的应用案例，如新闻报道、艺术创作、广告设计等；\r\n3. AIGC对社会经济、文化教育、道德伦理等方面的影响分析；\r\n4. AIGC的未来发展路径及可能面临的挑战；\r\n5. 如何利用AIGC技术来解决现实问题，提升生活质量。\r\n\r\n此外，会议还将设立专门的提问和交流环节，鼓励参会者积极参与讨论，提出自己的观点和建议。我们相信，通过这次会议，我们可以更深入地了解AIGC，挖掘其潜在价值，为推动科技发展、促进社会进步做出贡献。\r\n\r\n我们热切期待您的参与，一起探讨这个激动人心的新时代，共同创造一个更加美好的未来！\r\n\r\n敬请留意后续通知，关于会议的具体安排和注册方式。\r\n\r\n顺祝商祺，\r\n\r\n[你的名字]\r\n[你的职位]\r\n[联系邮箱]\r\n[联系电话]', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/049fa4872b094e4c946c79ed86f816ab.doc', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/4457ab929564423cb3769b719b049420.zip', '2023-12-21 17:56:31', 4, '通过审核！', '通过审核！');
INSERT INTO `report` VALUES (25, '探索未来科技新纪元：AIGC的革命性影响', '1001', '薛伟博士，是一位备受瞩目的AIGC领域专家。他在人工智能和计算机科学方面拥有深厚的知识背景，致力于将最新的研究成果转化为实际应用。薛博士在多个顶级国际会议和期刊上发表了多篇高质量的研究论文，并且获得了多项荣誉和奖项的认可。他不仅在学术界有着广泛的影响，而且在工业界也享有极高的声誉。他的研究成果对于推动AI技术的发展，以及解决现实世界中的复杂问题具有重要的意义。如果您对AIGC领域感兴趣，那么薛博士将是您的理想合作伙伴。', '我们非常荣幸地邀请您参加即将举行的“探索未来科技新纪元：AIGC的革命性影响”学术报告会议。此次会议将围绕人工智能生成内容（AIGC）这一前沿领域展开深入探讨，分享最新研究成果，展示其在科技创新和社会发展中的重要影响。\r\n\r\n近年来，随着计算机技术和机器学习的发展，AIGC已经取得了显著的进步。它涉及到自然语言处理、图像生成、音频合成等多个方面，正在改变我们获取信息、创作内容以及与世界互动的方式。在这个充满机遇和挑战的时代，我们需要更深入地理解AIGC的发展趋势，预测其可能带来的影响，并思考如何利用这些技术来推动科技进步和社会发展。\r\n\r\n本次会议将包括一系列主题演讲、小组讨论和技术展示环节，由来自全球顶尖大学、科研机构和企业的专家进行讲解和分享。他们将就以下几个方面展开讨论：\r\n1. AIGC的基本原理和发展历程；\r\n2. AIGC在各个领域的应用案例，如新闻报道、艺术创作、广告设计等；\r\n3. AIGC对社会经济、文化教育、道德伦理等方面的影响分析；\r\n4. AIGC的未来发展路径及可能面临的挑战；\r\n5. 如何利用AIGC技术来解决现实问题，提升生活质量。\r\n我们热切期待您的参与，一起探讨这个激动人心的新时代，共同创造一个更加美好的未来！', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/6eec41846f794c33b4c9f89fe0e1d692.doc', 'https://su-share.oss-cn-beijing.aliyuncs.com/刘毅/d8b81925f76848ada5a7b81344fe4eb1.zip', '2023-12-21 22:29:59', 4, '通过审核！', '通过审核！');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号【201724114111】',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期【1999-09-09】',
  `grade` int(4) NULL DEFAULT NULL COMMENT '年级【2017】',
  `major` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `class_info` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级信息【1班】',
  `college_id` int(11) NULL DEFAULT NULL COMMENT '学院ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '20171101', '薛伟', '20171101', '373675032@qq.com', '17879540499', '男', '2020-12-19', 2017, '计算机科学与技术', '1班', 1);
INSERT INTO `student` VALUES (2, '20171102', '杨光', '20171102', '373675033@qq.com', '17879540431', '男', '2020-12-08', 2017, '计算机科学与技术', '1班', 1);
INSERT INTO `student` VALUES (6, '20171104', '库里', '20171104', '372839232@qq.com', '17873404330', '女', '1978-12-19', 2017, '计算机科学与技术', '2班', 1);
INSERT INTO `student` VALUES (7, '20171105', '詹姆斯', '20171105', '372839232@qq.com', '17873404330', '女', '1978-12-19', 2017, '计算机科学与技术', '2班', 1);
INSERT INTO `student` VALUES (8, '20171106', '杜兰特', '20171106', '372839232@qq.com', '17873404330', '男', '1978-12-19', 2016, '计算机科学与技术', '2班', 1);
INSERT INTO `student` VALUES (9, '20171107', '汤姆森', '20171107', '372839232@qq.com', '17873404330', '女', '1978-12-19', 2017, '计算机科学与技术', '2班', 1);
INSERT INTO `student` VALUES (10, '20171108', '奥巴马', '20171108', '372839232@qq.com', '17873404330', '女', '1978-12-19', 2017, '计算机科学与技术', '2班', 1);
INSERT INTO `student` VALUES (11, '20171109', '特朗普', '20171109', '372839232@qq.com', '17873404330', '男', '1978-12-19', 2016, '计算机科学与技术', '2班', 1);
INSERT INTO `student` VALUES (12, '201711010', '维恩', '201711010', '372839232@qq.com', '17873404330', '女', '1978-12-19', 2016, '计算机科学与技术', '2班', 1);
INSERT INTO `student` VALUES (17, '20171111', '塞恩', '20171111', '372839232@qq.com', '17873404330', '女', '1978-12-19', 2017, '计算机科学与技术', '2班', 1);
INSERT INTO `student` VALUES (18, '20171112', '剑姬', '20171112', '372839232@qq.com', '17873404330', '男', '1978-12-19', 2016, '计算机科学与技术', '2班', 1);
INSERT INTO `student` VALUES (26, '201711018', '大炮', '201711018', '372839232@qq.com', '17873404330', '女', '1978-12-19', 2016, '计算机科学与技术', '2班', 1);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '职工号',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `birthday` date NULL DEFAULT NULL COMMENT '出生年月',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `position` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职称【助教、讲师、副教授、教授】',
  `is_college_leader` int(1) NULL DEFAULT 0 COMMENT '是否是院长【1是】【0否】',
  `college_id` int(11) NULL DEFAULT NULL COMMENT '学院ID',
  `is_department_leader` int(1) NULL DEFAULT 0 COMMENT '是否是部门部长【1是】【0否】',
  `department_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '1001', '刘毅', '1001', '17998734543', '1950-12-15', '男', '教授', 0, 1, 1, NULL);
INSERT INTO `teacher` VALUES (2, '1002', '陈尔', '1002', '17867894323', '1965-12-15', '男', '教授', 1, 1, 1, NULL);
INSERT INTO `teacher` VALUES (3, '1003', '张珊珊', '1003', '18547666666', '1975-06-15', '女', '副教授', 0, 1, 0, NULL);
INSERT INTO `teacher` VALUES (4, '1004', '李思思', '1004', '19678736879', '1984-06-12', '女', '讲师', 0, 1, 0, NULL);
INSERT INTO `teacher` VALUES (5, '1005', '王武', '1005', '13978978967', '1968-04-12', '男', '教授', 0, 2, 0, NULL);
INSERT INTO `teacher` VALUES (6, '1006', '赵柳', '1006', '13789076855', '1988-08-06', '男', '助教', 1, 1, 0, NULL);
INSERT INTO `teacher` VALUES (7, '1007', '孙琪琪', '1007', '18563472985', '1978-05-11', '女', '副教授', 0, 1, 0, NULL);
INSERT INTO `teacher` VALUES (8, '1008', '周抜', '1008', '15789237423', '1990-12-10', '男', '讲师', 0, 1, 0, NULL);
INSERT INTO `teacher` VALUES (9, '1009', '吴久久', '1009', '15836834534', '1981-11-15', '女', '讲师', 0, 1, 0, NULL);
INSERT INTO `teacher` VALUES (10, '1010', '郑世', '1010', '17896738432', '1995-12-15', '男', '助教', 0, 1, 0, NULL);
INSERT INTO `teacher` VALUES (11, '1015', '沙悟净', '1015', '15954734543', '1959-12-19', '男', '助教', 0, 2, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
