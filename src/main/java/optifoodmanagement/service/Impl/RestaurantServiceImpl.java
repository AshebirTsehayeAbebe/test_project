package optifoodmanagement.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import optifoodmanagement.exception.AppException;
import optifoodmanagement.io.entity.Contact;
import optifoodmanagement.io.entity.Restaurant;
import optifoodmanagement.io.entity.UserEntity;
import optifoodmanagement.io.repositories.ContactRepository;
import optifoodmanagement.io.repositories.RestaurantRepository;
import optifoodmanagement.io.repositories.UserRepository;
import optifoodmanagement.service.RestaurantService;
import optifoodmanagement.shared.GenerateRandomStrings;
import optifoodmanagement.ui.model.request.LogoRequestModel;
import optifoodmanagement.ui.model.request.RestaurantRequestModel;
import optifoodmanagement.ui.model.response.RestaurantResponseModel;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	GenerateRandomStrings generateRandomString;
	
	@Autowired
	EntityManager entityManager;
	
	@Override
	public RestaurantResponseModel saveRestaurant(RestaurantRequestModel requestDetail) {
		RestaurantResponseModel returnValue = new RestaurantResponseModel();
		Restaurant restaurant = new Restaurant();
		BeanUtils.copyProperties(requestDetail, restaurant);
		String licenseKey = "";
		for (int i = 0; i < 100; i++) {
			licenseKey = generateRandomString.generateUserId(24);
			Restaurant checkRestaurant = restaurantRepository.findByLicenseKeyAndIsDeleted(licenseKey, false);
			if (checkRestaurant == null)
				break;
			
		}
		restaurant.setLicenseKey(licenseKey);
		Restaurant savedDetail = restaurantRepository.save(restaurant);
		
		if (savedDetail != null) {
			try {
				createDB(savedDetail.getRestaurantName().replaceAll(" ", "_").toLowerCase());
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (savedDetail == null)
			throw new AppException("Record not saved!");
		BeanUtils.copyProperties(savedDetail, returnValue);
		return returnValue;
	}
	
	@Override
	public RestaurantResponseModel updateRestaurant(Integer restaurantId, RestaurantRequestModel requestDetail) {
		
		RestaurantResponseModel returnValue = new RestaurantResponseModel();
		Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(restaurantId, false);
		
		if (restaurant == null)
			throw new AppException("No record with this id!");
		BeanUtils.copyProperties(requestDetail, restaurant);
		Restaurant savedDetail = restaurantRepository.save(restaurant);
		
		if (savedDetail == null)
			throw new AppException("Record not updated!");
		BeanUtils.copyProperties(savedDetail, returnValue);
		return returnValue;
		
	}
	
	@Override
	public String deleteRestaurant(Integer restaurantId) {
		
		List<UserEntity> userEntities = userRepository.findByRestaurantId(restaurantId);
		if (userEntities.size() > 0)
			throw new AppException("Could not delete a restaurnt that has user!");
		
		Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(restaurantId, false);
		if (restaurant == null)
			throw new AppException("No record with this id!");
		restaurant.setDeleted(true);
		Restaurant savedDetail = restaurantRepository.save(restaurant);
		
		if (savedDetail == null)
			throw new AppException("Record not deleted!");
		return "Deleted successfully!";
		
	}
	
	@Override
	public List<RestaurantResponseModel> getRestaurants(String searchKey, int contactId, int chainId, int page, int limit) {
		
		List<RestaurantResponseModel> returnValue = new ArrayList<>();
		
		
		
		//		String attributesInfo = "r.restaurantId, r.restaurantName, r.licenseKey, r.openingTime, r.closingTime, r.address,"
		//				+ "r.cityName, r.postalCode, r.contactId, chainId";
		//		
		//		String fromCondition = " from restaurant r";
		//		
		//		String whereCondition = " where (r.contactId=:contactId OR 0=:contactId) AND (r.chainId=:chainId OR 0=:chainId)"
		//		        + " AND r.isDeleted=0 order by r.restaurantId desc";
		//		List<Object[]> results = new ArrayList<Object[]>();
		//		
		//		results = entityManager.createQuery("SELECT " + attributesInfo + fromCondition + whereCondition, Object[].class)
		//		        .setParameter("contactId", contactId).setParameter("chainId", chainId).setFirstResult((page) * limit)
		//		        .setMaxResults(limit)
		//		        .getResultList();
		//		
		//		Integer totalRows = (int) entityManager.createQuery("SELECT count(*)" + fromCondition + whereCondition)
		//		        .setParameter("contactId", contactId).setParameter("chainId", chainId).getSingleResult();
		//		
		//		for (Object[] result : results) {
		//			RestaurantResponseModel restaurantResponseModel = new RestaurantResponseModel();
		//			restaurantResponseModel.setRestaurantId((Integer) result[0]);
		//			restaurantResponseModel.setRestaurantName((String) result[1]);
		//			restaurantResponseModel.setLicenseKey((String) result[2]);
		//			restaurantResponseModel.setOpeningTime((Time) result[3]);
		//			restaurantResponseModel.setClosingTime((Time) result[4]);
		//			restaurantResponseModel.setAddress((String) result[5]);
		//			restaurantResponseModel.setCityName((String) result[6]);
		//			restaurantResponseModel.setPostalCode((String) result[7]);
		//			restaurantResponseModel.setContactId((Integer) result[8]);
		//			restaurantResponseModel.setChainId((Integer) result[9]);
		//			if (returnValue.size() == 0) {
		//				if (totalRows % limit == 0)
		//					restaurantResponseModel.setTotalPage(totalRows / limit);
		//				else
		//					restaurantResponseModel.setTotalPage((totalRows / limit) + 1);
		//			}
		//			returnValue.add(restaurantResponseModel);
		//			
		//		}
		if (page > 0)
			page = page - 1;
		
		Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("restaurantId").descending());
		Page<Restaurant> restaurantPage = null;
		
		if ("".equals(searchKey) && contactId == 0 && chainId == 0)
			restaurantPage = restaurantRepository.findByIsDeleted(false, pageableRequest);
		
		else if ("".equals(searchKey) && contactId == 0 && chainId != 0)
			restaurantPage = restaurantRepository.findByChainIdAndIsDeleted(chainId, false, pageableRequest);
		
		else if ("".equals(searchKey) && contactId != 0 && chainId == 0)
			restaurantPage = restaurantRepository.findByContactIdAndIsDeleted(contactId, false, pageableRequest);
		
		else if ("".equals(searchKey) && contactId != 0 && chainId != 0)
			restaurantPage = restaurantRepository.findByContactIdAndChainIdAndIsDeleted(contactId, chainId, false,
			    pageableRequest);
		
		else if (!"".equals(searchKey) && contactId == 0 && chainId == 0)
			restaurantPage = restaurantRepository.findByIsDeletedAndRestaurantNameContaining(false, searchKey,
			    pageableRequest);
		
		else if (!"".equals(searchKey) && contactId == 0 && chainId != 0)
			restaurantPage = restaurantRepository.findByRestaurantNameAndChainIdAndIsDeleted(searchKey, chainId, false,
			    pageableRequest);
		
		else if (!"".equals(searchKey) && contactId != 0 && chainId == 0)
			restaurantPage = restaurantRepository.findByRestaurantNameAndContactIdAndIsDeleted(searchKey, contactId, false,
			    pageableRequest);
		
		else
			restaurantPage = restaurantRepository.findByRestaurantNameAndContactIdAndChainIdAndIsDeleted(searchKey,
			    contactId,
			    chainId,
			    false,
			    pageableRequest);
		
		List<Restaurant> restaurants = restaurantPage.getContent();
		
		int totalPages = restaurantPage.getTotalPages();
		for (Restaurant restaurant : restaurants) {
			
			RestaurantResponseModel responseModel = new RestaurantResponseModel();
			BeanUtils.copyProperties(restaurant, responseModel);
			
			Contact contact = contactRepository
			        .findByContactIdAndIsDeleted(restaurant.getContactId(), false);
			
			if (contact != null)
				responseModel.setContactName(contact.getFirstName() + " " + contact.getName());
			if (returnValue.size() == 0) {
				responseModel.setTotalPage(totalPages);
			}
			
			returnValue.add(responseModel);
		}
		return returnValue;
		
	}

	@Override
	public RestaurantResponseModel getRestaurant(Integer restaurantId) {
		RestaurantResponseModel returnValue = new RestaurantResponseModel();
		Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(restaurantId, false);
		
		
		
		if (restaurant == null)
			throw new AppException("No record with this id!");
		
		BeanUtils.copyProperties(restaurant, returnValue);
		return returnValue;
	}
	
	@Override
	public String resetLicenseKey(Integer restaurantId) {
		RestaurantResponseModel returnValue = new RestaurantResponseModel();
		Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(restaurantId, false);
		
		if (restaurant == null)
			throw new AppException("No record with this id!");
		String licenseKey = "";
		for (int i = 0; i < 100; i++) {
			licenseKey = generateRandomString.generateUserId(24);
			Restaurant checkRestaurant = restaurantRepository.findByLicenseKeyAndIsDeleted(licenseKey, false);
			if (checkRestaurant == null)
				break;
			
		}
		restaurant.setLicenseKey(licenseKey);
		Restaurant savedDetail = restaurantRepository.save(restaurant);
		
		if (savedDetail == null)
			throw new AppException("Record not updated!");
		BeanUtils.copyProperties(savedDetail, returnValue);
		return "License key reseted";
	}
	
	@Override
	public String uploadLogo(LogoRequestModel requestDetail) throws IOException {
		//		String rootDirectory = "/var/www/html";
		String rootDirectory = "C://wamp64/www/";
		String uploadDir = rootDirectory + "/logos/";
		
		File directory = new File(uploadDir);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		Integer restauarantId = requestDetail.getRestaurantId();
		Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeleted(restauarantId, false);
		if (restaurant == null)
			throw new AppException("Restaurant not found.");
		
		String returnValue = "Image not saved";
		byte[] bytes = requestDetail.getLogo().getBytes();
		
		String fileName = requestDetail.getLogo().getOriginalFilename();
		String extention = (fileName.substring(fileName.lastIndexOf(".") + 1)).toLowerCase();
		String newFileName = restaurant.getLicenseKey() + "." + extention;
		Path path = Paths.get(uploadDir + newFileName);
		Files.write(path, bytes);
		
		
		
		restaurant.setLogo(newFileName);
		Restaurant saveRestaurant = restaurantRepository.save(restaurant);
		if (saveRestaurant.getLogo() != null) {
			returnValue = "Logo picture Saved";
		}
		
		return returnValue;
	}
	
	public String createDB(String db) throws SQLException {
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tenant1", "openpg", "openpgpwd");
			Statement smt = con.createStatement();
			smt.executeUpdate("CREATE DATABASE " + db);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return "DB Created!";
	}
	
}
