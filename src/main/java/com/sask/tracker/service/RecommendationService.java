package com.sask.tracker.service;

import com.sask.tracker.model.Recommendation;
import com.sask.tracker.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    //  Get recommendations by age and category
    public List<Recommendation> getRecommendationsByAge(int age) {
        return recommendationRepository.findByAge(age);
    }

    public String getRecommendationForAgeAndCategory(int age, String category) {
        List<Recommendation> recommendations = recommendationRepository.findByAgeAndCategory(age, category);

        if (recommendations != null && !recommendations.isEmpty()) {
            //  Combine multiple recommendations into a single string using '\n'
            return recommendations.stream()
                    .map(Recommendation::getRecommendation)
                    .collect(Collectors.joining("\n"));
        }

        return null;
    }
}
