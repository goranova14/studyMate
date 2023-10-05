package nl.fontys.s3.studysmate.studymate.business;


import nl.fontys.s3.studysmate.studymate.domain.Grade;

public interface GetGradeUseCase {
   Grade getGrade(long assignmentId, long studentPcn);
}
