-- Journal Records
INSERT INTO JournalRecords (journalRecordId, fullName, birthday, isFullTimeEducationForm, password) VALUES(1, 'Ivan Tolkunov', '2001-07-07', 1, 'fghxfhgrt');
INSERT INTO JournalRecords (journalRecordId, fullName, birthday, isFullTimeEducationForm, password) VALUES(2, 'Alina Lytovchenko', '2004-06-20', 1, 'dgshddfshets');
INSERT INTO JournalRecords (journalRecordId, fullName, birthday, isFullTimeEducationForm, password) VALUES(3, 'Maksym Bidnyi', '2004-02-20', 1, 'tls[htphls');
INSERT INTO JournalRecords (journalRecordId, fullName, birthday, isFullTimeEducationForm, password) VALUES(4, 'Matylda Starzynska', '1999-05-29', 1, 'dsehkprtghp[r');
INSERT INTO JournalRecords (journalRecordId, fullName, birthday, isFullTimeEducationForm, password) VALUES(5, 'Anna Ivasishyna', '2004-02-23', 1, 'dtehtrhets');

-- Professors
INSERT INTO Professors (professorId, fullName, position, password) VALUES(1, 'Svitlana Proskura', 'Dean', 'fkgldfkr');
INSERT INTO Professors (professorId, fullName, position, password) VALUES(2, 'Yaroslav Kornaga', 'Professor', 'h5eytsru');
INSERT INTO Professors (professorId, fullName, position, password) VALUES(3, 'Yevgeniy Vovk', 'Professor', 'rgerhtrjhyrj');
INSERT INTO Professors (professorId, fullName, position, password) VALUES(4, 'Yuliia Kuliasha', 'Professor', 'sfgfdsgz');
INSERT INTO Professors (professorId, fullName, position, password) VALUES(5, 'Volodymyr Popenko', 'Professor', 'rsgzgrwagrew');

-- Lessons
INSERT INTO Lessons (lessonId, subjectName, professorId, weekDay, classNumber) VALUES(1, 'Algorithms', 3, 'Monday', 3);
INSERT INTO Lessons (lessonId, subjectName, professorId, weekDay, classNumber) VALUES(2, 'Data Structures', 2, 'Friday', 2);
INSERT INTO Lessons (lessonId, subjectName, professorId, weekDay, classNumber) VALUES(3, 'Java', 1, 'Friday', 4);
INSERT INTO Lessons (lessonId, subjectName, professorId, weekDay, classNumber) VALUES(4, 'Operational Systems', 4, 'Thursday', 2);
INSERT INTO Lessons (lessonId, subjectName, professorId, weekDay, classNumber) VALUES(5, 'Philosophy', 3, 'Monday', 6);

-- Marks
INSERT INTO Marks (markId, studentId, lessonId, date, mark) VALUES(1, 2, 3, '2023-12-16', 9);
INSERT INTO Marks (markId, studentId, lessonId, date, mark) VALUES(2, 4, 1, '2023-12-15', 10);
INSERT INTO Marks (markId, studentId, lessonId, date, mark) VALUES(3, 3, 3, '2023-12-17', 7);
INSERT INTO Marks (markId, studentId, lessonId, date, mark) VALUES(4, 4, 2, '2023-12-13', 6);
INSERT INTO Marks (markId, studentId, lessonId, date, mark) VALUES(5, 1, 5, '2023-12-11', 10);
