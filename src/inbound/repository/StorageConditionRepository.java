package inbound.repository;

public interface StorageConditionRepository {

    /**
     * 특정 보관 상태(storage_id)의 최소/최대 온도 범위를 조회하고,
     * 제품의 보관 온도(storageTemp)가 이 범위 내에 포함되는지 확인한다.
     *
     * @param storageId 창고의 보관 상태 ID
     * @param storageTemp 제품의 보관 온도
     * @return 보관 가능하면 true, 불가능하면 false
     */
    boolean isStorageConditionMatch(int storageId, int storageTemp);
}
