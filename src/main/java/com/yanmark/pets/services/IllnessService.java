package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.bindings.illnesses.IllnessCreateBindingModel;
import com.yanmark.pets.domain.models.bindings.illnesses.IllnessEditBindingModel;
import com.yanmark.pets.domain.models.services.IllnessServiceModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;

import java.io.IOException;
import java.util.List;

public interface IllnessService {

  IllnessServiceModel saveIllness(IllnessCreateBindingModel illnessCreate, String healthId)
      throws IOException;

  IllnessServiceModel updateIllness(
      IllnessServiceModel illnessService, IllnessEditBindingModel illnessEdit) throws IOException;

  IllnessServiceModel getIllnessById(String id);

  List<IllnessServiceModel> getIllnessesByPet(PetServiceModel petService);

  void deleteIllness(String id);
}
