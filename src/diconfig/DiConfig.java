package diconfig;

import area.controller.AreaController;
import area.controller.AreaControllerImp;
import area.repository.AreaRepository;
import area.repository.AreaRepositoryImp;
import area.service.AreaService;
import area.service.AreaServiceImp;
import inbound.controller.*;
import inbound.repository.*;
import inbound.service.*;
import login.controller.LoginCont;
import login.controller.LoginContImpl;
import login.repository.LoginRepo;
import login.repository.LoginRepoImpl;
import login.service.LoginService;
import login.service.LoginServiceImpl;
import outbound.controller.OutboundController;
import outbound.controller.OutboundControllerImpl;
import outbound.repository.OutboundRepository;
import outbound.repository.OutboundRepositoryImpl;
import outbound.service.OutboundService;
import outbound.service.OutboundServiceImpl;
import user.controller.AdminCont;
import user.controller.AdminContImpl;
import user.repository.UserRepo;
import user.repository.UserRepoImpl;
import user.service.UserService;
import user.service.UserServiceImpl;
import warehouse.controller.WareHouseController;
import warehouse.controller.WareHouseControllerImp;
import warehouse.repository.WareHouseRepository;
import warehouse.repository.WareHouseRepositoryImp;
import warehouse.service.WareHouseService;
import warehouse.service.WareHouseServiceImp;

public class DiConfig {

    //warehouse Di
    //-----------------------------------------------------------------
    private WareHouseRepository getWareHouseRepository() {
        return new WareHouseRepositoryImp();
    }
    private WareHouseService getWarehouseService() {
        return new WareHouseServiceImp(getWareHouseRepository());
    }
    public WareHouseController getWarehouseController() {
        return new WareHouseControllerImp(getWarehouseService());
    }
    //-----------------------------------------------------------------

    //area Di
    //-----------------------------------------------------------------
    public AreaRepository getAreaRepository() {
        return new AreaRepositoryImp();
    }
    public AreaService getAreaService() {
        return new AreaServiceImp(getAreaRepository(),getWareHouseRepository());
    }
    public AreaController getAreaController(){
        return new AreaControllerImp(getAreaService());
    }
    //-----------------------------------------------------------------

    //inbound Di
    //-----------------------------------------------------------------
    public InboundRepository getInboundRepository() {
        return new InboundRepositoryImp();
    }
    public ProductRepository getProductRepository() {
        return new ProductRepositoryImp();
    }
    public StorageConditionRepository getStorageConditionRepository() {
        return new StorageConditionRepositoryImp();
    }
    public RevenueRepository getRevenueRepository() {
        return new RevenueRepositoryImp();
    }

    public InboundService getInboundService() {
        return new InboundServiceImp(getInboundRepository()
                , getProductRepository()
                , getStorageConditionRepository()
                , getRevenueRepository()
                , getAreaService());
    }

    public InboundController getInboundController(){
        return new InboundControllerImp(getInboundService());
    }
    //-----------------------------------------------------------------

    //outbound Di
    //-----------------------------------------------------------------
    public OutboundRepository getOutboundRepository() {
        return new OutboundRepositoryImpl();
    }

    public OutboundService getOutboundService() {
        return new OutboundServiceImpl(getOutboundRepository());
    }

    public OutboundController getOutboundController() {
        return new OutboundControllerImpl(getOutboundService());
    }
    //-----------------------------------------------------------------

    //login Di
    //-----------------------------------------------------------------
    public LoginRepo getLoginRepository() {
        return new LoginRepoImpl();
    }
    public LoginService getLoginService() {
        return new LoginServiceImpl(getLoginRepository());
    }
    public LoginCont getLoginController() {
        return new LoginContImpl(getLoginService(),getUserService());
    }

    //user Di

    public UserRepo getUserRepository() {
        return new UserRepoImpl();
    }
    public UserService getUserService() {
        return new UserServiceImpl(getUserRepository());
    }
    public AdminCont getAdminController() {
        return new AdminContImpl(getUserService(),getLoginController());
    }

    //protect Di
    public ProductRepository productRepository() {
        return new ProductRepositoryImp();
    }

    public ProductService getProductService() {
        return new ProductServiceImp(getProductRepository());
    }

    public ProductController getProductController() {
        return new ProductControllerImp(getProductService());
    }

    //revenue Di

    public RevenueRepository revenueRepository() {
        return new RevenueRepositoryImp();
    }
    public RevenueHistoryService getRevenueHistoryService() {
        return new RevenueHistoryServiceImp(getRevenueRepository());
    }

    public RevenueHistoryController getRevenueHistoryController() {
        return new RevenueHistoryControllerImp(getRevenueHistoryService());
    }

}
