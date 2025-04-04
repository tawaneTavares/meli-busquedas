package com.tawane.data.local.dao

import app.cash.turbine.test
import com.tawane.data.mock.MockResponse.expectedList
import com.tawane.data.mock.MockResponse.lastViewedItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LastViewedDaoTest {

    private val lastViewedDao: LastViewedDao = mockk(relaxed = true)

    @Test
    fun `insertLastViewedItem should call insertLastViewedItem on dao`() = runTest {
        lastViewedDao.insertLastViewedItem(lastViewedItem)

        coVerify { lastViewedDao.insertLastViewedItem(lastViewedItem) }
    }

    @Test
    fun `getLastViewedItems should return correct list`() = runTest {
        coEvery { lastViewedDao.getLastViewedItems() } returns flowOf(expectedList)

        lastViewedDao.getLastViewedItems().test {
            val actualList = awaitItem()
            assert(actualList == expectedList)
            awaitComplete()
        }
    }

    @Test
    fun `deleteOldLastViewedItems should call deleteOldLastViewedItems on dao`() = runTest {
        lastViewedDao.deleteOldLastViewedItems()

        coVerify { lastViewedDao.deleteOldLastViewedItems() }
    }
}
