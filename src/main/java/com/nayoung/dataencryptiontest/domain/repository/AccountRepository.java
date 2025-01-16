package com.nayoung.dataencryptiontest.domain.repository;

import com.nayoung.dataencryptiontest.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
