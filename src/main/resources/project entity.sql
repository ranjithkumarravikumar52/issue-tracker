-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema issue-tracker
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `issue-tracker` ;

-- -----------------------------------------------------
-- Schema issue-tracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `issue-tracker` DEFAULT CHARACTER SET utf8 ;
USE `issue-tracker` ;

-- -----------------------------------------------------
-- Table `issue-tracker`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `issue-tracker`.`users` ;

CREATE TABLE IF NOT EXISTS `issue-tracker`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `user_role` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `issue-tracker`.`issues`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `issue-tracker`.`issues` ;

CREATE TABLE IF NOT EXISTS `issue-tracker`.`issues` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `issue_description` VARCHAR(120) NULL DEFAULT NULL,
  `posted_by` INT(11) NOT NULL,
  `opened_by` INT(11) NULL DEFAULT NULL,
  `fixed_by` INT(11) NULL DEFAULT NULL,
  `closed_by` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_issues_users_idx` (`posted_by` ASC),
  INDEX `fk_issues_users1_idx` (`opened_by` ASC),
  INDEX `fk_issues_users2_idx` (`fixed_by` ASC),
  INDEX `fk_issues_users3_idx` (`closed_by` ASC),
  CONSTRAINT `fk_issues_users`
    FOREIGN KEY (`posted_by`)
    REFERENCES `issue-tracker`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_issues_users1`
    FOREIGN KEY (`opened_by`)
    REFERENCES `issue-tracker`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_issues_users2`
    FOREIGN KEY (`fixed_by`)
    REFERENCES `issue-tracker`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_issues_users3`
    FOREIGN KEY (`closed_by`)
    REFERENCES `issue-tracker`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `issue-tracker`.`project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `issue-tracker`.`project` ;

CREATE TABLE IF NOT EXISTS `issue-tracker`.`project` (
  `id` INT NOT NULL,
  `project_description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `issue-tracker`.`users_has_project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `issue-tracker`.`users_has_project` ;

CREATE TABLE IF NOT EXISTS `issue-tracker`.`users_has_project` (
  `users_id` INT(11) NOT NULL,
  `project_id` INT NOT NULL,
  PRIMARY KEY (`users_id`, `project_id`),
  INDEX `fk_users_has_project_project1_idx` (`project_id` ASC),
  INDEX `fk_users_has_project_users1_idx` (`users_id` ASC),
  CONSTRAINT `fk_users_has_project_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `issue-tracker`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_project_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `issue-tracker`.`project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `issue-tracker`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `issue-tracker`;
INSERT INTO `issue-tracker`.`users` (`id`, `user_name`, `password`, `email`, `user_role`) VALUES (1, 'mark', 'fun123', 'mark@gmail.com', 'developer');
INSERT INTO `issue-tracker`.`users` (`id`, `user_name`, `password`, `email`, `user_role`) VALUES (2, 'ryan', 'fun321', 'ryan@gmail.com', 'developer');
INSERT INTO `issue-tracker`.`users` (`id`, `user_name`, `password`, `email`, `user_role`) VALUES (3, 'mary', 'fun132', 'mary@gmail.com', 'tester');
INSERT INTO `issue-tracker`.`users` (`id`, `user_name`, `password`, `email`, `user_role`) VALUES (4, 'susan', 'fun312', 'susan@gmail.com', 'tester');
INSERT INTO `issue-tracker`.`users` (`id`, `user_name`, `password`, `email`, `user_role`) VALUES (5, 'karen', 'adminpassword', 'karen@gmail.com', 'admin');

COMMIT;


-- -----------------------------------------------------
-- Data for table `issue-tracker`.`issues`
-- -----------------------------------------------------
START TRANSACTION;
USE `issue-tracker`;
INSERT INTO `issue-tracker`.`issues` (`id`, `issue_description`, `posted_by`, `opened_by`, `fixed_by`, `closed_by`) VALUES (1, 'test issue at home', 3, 2, NULL, NULL);
INSERT INTO `issue-tracker`.`issues` (`id`, `issue_description`, `posted_by`, `opened_by`, `fixed_by`, `closed_by`) VALUES (2, 'blocker at home', 2, 2, 3, NULL);
INSERT INTO `issue-tracker`.`issues` (`id`, `issue_description`, `posted_by`, `opened_by`, `fixed_by`, `closed_by`) VALUES (3, 'graphics at about', 4, 2, 3, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `issue-tracker`.`project`
-- -----------------------------------------------------
START TRANSACTION;
USE `issue-tracker`;
INSERT INTO `issue-tracker`.`project` (`id`, `project_description`) VALUES (1, 'core module');
INSERT INTO `issue-tracker`.`project` (`id`, `project_description`) VALUES (2, 'front end module');
INSERT INTO `issue-tracker`.`project` (`id`, `project_description`) VALUES (3, 'back end module');

COMMIT;


-- -----------------------------------------------------
-- Data for table `issue-tracker`.`users_has_project`
-- -----------------------------------------------------
START TRANSACTION;
USE `issue-tracker`;
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (1, 1);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (1, 2);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (1, 3);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (2, 3);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (2, 2);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (3, 1);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (4, 1);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (4, 2);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (4, 3);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (5, 1);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (5, 2);
INSERT INTO `issue-tracker`.`users_has_project` (`users_id`, `project_id`) VALUES (5, 3);

COMMIT;

