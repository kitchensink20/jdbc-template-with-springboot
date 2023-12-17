DROP TABLE IF EXISTS Marks;
DROP TABLE IF EXISTS Lessons;
DROP TABLE IF EXISTS Professors;
DROP TABLE IF EXISTS JournalRecords;

CREATE TABLE `JournalRecords` (
    `journalRecordId` int(11) NOT NULL AUTO_INCREMENT,
    `fullName` varchar(100) NOT NULL,
    `birthday` date NOT NULL,
    `isFullTimeEducationForm` tinyint(4) NOT NULL,
    `password` varchar(100) NOT NULL,
    PRIMARY KEY (`journalRecordId`)
);

CREATE TABLE `Professors` (
    `professorId` int(11) NOT NULL AUTO_INCREMENT,
    `fullName` varchar(100) NOT NULL,
    `position` varchar(80) NOT NULL,
    `password` varchar(50) NOT NULL,
    PRIMARY KEY (`professorId`)
);

CREATE TABLE `Lessons` (
    `lessonId` INT NOT NULL AUTO_INCREMENT,
    `subjectName` VARCHAR(100) NOT NULL,
    `professorId` INT NULL,
    `weekDay` VARCHAR(45) NOT NULL,
    `classNumber` INT NOT NULL,
    PRIMARY KEY (`lessonId`),
    INDEX `professorId_idx` (`professorId` ASC) VISIBLE,
    CONSTRAINT `professorId`
    FOREIGN KEY (`professorId`)
        REFERENCES `Professors` (`professorId`)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT `invalidWeekDay` CHECK ('weekDay' not in ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday')));

CREATE TABLE `Marks` (
    `markId` int(11) NOT NULL AUTO_INCREMENT,
    `studentId` int(11) NOT NULL,
    `lessonId` int(11) NOT NULL,
    `date` date NOT NULL,
    `mark` decimal(4,2) NOT NULL,
    PRIMARY KEY (`markId`),
    UNIQUE KEY `markId_UNIQUE` (`markId`),
    KEY `lessonId_idx` (`lessonId`),
    KEY `studentId_idx` (`studentId`),
    CONSTRAINT `lessonId` FOREIGN KEY (`lessonId`) REFERENCES `Lessons` (`lessonId`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `studentId` FOREIGN KEY (`studentId`) REFERENCES `JournalRecords` (`journalRecordId`) ON DELETE CASCADE ON UPDATE CASCADE
)