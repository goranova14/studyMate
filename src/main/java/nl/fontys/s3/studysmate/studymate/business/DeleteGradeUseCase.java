package nl.fontys.s3.studysmate.studymate.business;

public interface DeleteGradeUseCase {
    void deleteGrade(long assignmentId,long studentPcn);
}
