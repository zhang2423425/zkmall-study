CREATE TABLE `cere_ai_default_question` (
`questions_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
`question_content` text COMMENT '问题内容',
`answer_list` json DEFAULT NULL COMMENT '问题答案 （多个答案 json格式）',
`create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
`update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
PRIMARY KEY (`questions_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='Ai默认问题表';


-- cere_platform_permission表增量sql -> 菜单配置
INSERT INTO `cere_platform_permission` VALUES (12122, 1, 'AI回复配置', '', '/aiconfig/replyconfig/index', 'ChatSquare', '配置ai默认回复', 'menu', 10695, 1, '2025-09-18 16:32:16', NULL);
-- cere_platform_role_permission表增量sql -> 角色菜单授权，该sql用于给管理员角色授权
INSERT INTO `cere_platform_role_permission` VALUES (1, 12122);

ALTER TABLE `cere_buyer_label`
    ADD UNIQUE INDEX `cree_buyer_lable_uidx`(`buyer_user_id`, `buyer_label_id`);