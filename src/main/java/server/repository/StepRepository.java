package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.entity.process.step.Step;
import server.entity.process.step.StepId;

import java.util.List;

/**
 * Репозиторий для сущности "Шаг"
 * @see Step
 * */
@Repository
@Transactional
public interface StepRepository extends JpaRepository<Step, StepId>, CrudRepository<Step, StepId> {
    
    @Transactional
    <S extends Step> List<S> saveAll(Iterable<S> entities);
    
    @Transactional
    <S extends Step> S saveAndFlush(S entity);
}
