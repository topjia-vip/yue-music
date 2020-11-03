/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : music_music

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 02/07/2020 17:32:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for disst
-- ----------------------------
DROP TABLE IF EXISTS `disst`;
CREATE TABLE `disst`  (
  `disst_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `disst_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `disst_logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `disst_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `head_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `song_num` int(0) NOT NULL,
  `visitnum` int(0) NOT NULL,
  PRIMARY KEY (`disst_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for singer
-- ----------------------------
DROP TABLE IF EXISTS `singer`;
CREATE TABLE `singer`  (
  `singer_id` int(0) NOT NULL,
  `singer_mid` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `singer_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `singer_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`singer_id`) USING BTREE,
  INDEX `tbl_singer_index_singerMid`(`singer_mid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for singer_song
-- ----------------------------
DROP TABLE IF EXISTS `singer_song`;
CREATE TABLE `singer_song`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `song_mid` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `singer_mid` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tbl_singer_song_index_singerMid_songMid`(`song_mid`, `singer_mid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for song
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song`  (
  `id` int(0) NOT NULL COMMENT '歌曲id',
  `mid` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '歌曲mid',
  `media_mid` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '歌曲媒体资源mid',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '歌曲名称',
  `album` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '歌曲所属的专辑ID',
  `duration` int(0) NOT NULL COMMENT '歌曲播放时长',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '歌曲图片URL',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '歌曲标题',
  `sub_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '歌曲额外标题',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tbl_song_index_mid`(`mid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_like_dissts
-- ----------------------------
DROP TABLE IF EXISTS `user_like_dissts`;
CREATE TABLE `user_like_dissts`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `disst_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '歌单id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tbl_user_like_dissts_index_userId_disstId`(`user_id`, `disst_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_like_songs
-- ----------------------------
DROP TABLE IF EXISTS `user_like_songs`;
CREATE TABLE `user_like_songs`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `song_id` int(0) NOT NULL COMMENT '歌曲id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tbl_user_like_songs_index_userId_songId`(`user_id`, `song_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
