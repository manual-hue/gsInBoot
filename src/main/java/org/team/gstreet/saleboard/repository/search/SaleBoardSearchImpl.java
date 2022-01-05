package org.team.gstreet.saleboard.repository.search;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import org.team.gstreet.saleboard.entity.*;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
public class  SaleBoardSearchImpl extends QuerydslRepositorySupport implements SaleBoardSearch {

    public SaleBoardSearchImpl() {

        super(SaleBoard.class);
    }


    @Override
    public Page<SaleBoard> search(char[] typeArr, String keyword, Pageable pageable) {
        log.info("search1");

        QSaleBoard saleBoard = QSaleBoard.saleBoard;

        JPQLQuery<SaleBoard> saleBoardJPQLQuery = from(saleBoard);


        if (typeArr != null && typeArr.length > 0) {
            BooleanBuilder builder = new BooleanBuilder();
            for (char type : typeArr) {
                if (type == 'T') {
                    builder.or(saleBoard.stitle.contains(keyword));
                } else if (type == 'C') {
                    builder.or(saleBoard.scontent.contains(keyword));
                } else if (type == 'W') {
                    builder.or(saleBoard.swriter.contains(keyword));
                } else if (type == 'E') {  //카테고리검색
                    builder.or(saleBoard.scategory.contains(keyword));
                }else if(type == 'H') { //해시태그 검색
                    builder.or(saleBoard.tags.contains(keyword));
                }
            }
            saleBoardJPQLQuery.where(builder);
        }
        saleBoardJPQLQuery.where(saleBoard.sno.gt(0L)); //bno > 0

        JPQLQuery<SaleBoard> pagingQuery = this.getQuerydsl().applyPagination(pageable, saleBoardJPQLQuery);

        List<SaleBoard> saleBoardList = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount();


        return new PageImpl<>(saleBoardList, pageable, totalCount);

    }

    @Override
    public Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword, Pageable pageable) {

        QSaleBoard qsaleBoard = QSaleBoard.saleBoard;
        QSaleReply qSaleReply= QSaleReply.saleReply;

        JPQLQuery<SaleBoard> query = from(qsaleBoard);
        query.leftJoin(qSaleReply).on(qSaleReply.saleBoard.eq(qsaleBoard));
        query.groupBy(qsaleBoard);

        if (typeArr != null && typeArr.length > 0) {
            BooleanBuilder builder = new BooleanBuilder();
            for (char type : typeArr) {
                if (type == 'T') {
                    builder.or(qsaleBoard.stitle.contains(keyword));
                } else if (type == 'C') {
                    builder.or(qsaleBoard.scontent.contains(keyword));
                } else if (type == 'W') {
                    builder.or(qsaleBoard.swriter.contains(keyword));
                } else if (type == 'E') {  //카테고리검색
                    builder.or(qsaleBoard.scategory.contains(keyword));
                }
            }
            query.where(builder);
        }

        JPQLQuery<Tuple> selectQuery = query.select(qsaleBoard.sno, qsaleBoard.stitle,
                qsaleBoard.swriter, qsaleBoard.scategory,qsaleBoard.sreg_date,qSaleReply.count());
        this.getQuerydsl().applyPagination(pageable, selectQuery);

        log.info(selectQuery);

        List<Tuple> tupleList = selectQuery.fetch();

        long totalCount = selectQuery.fetchCount();

        List<Object[]> arr = tupleList.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

        return new PageImpl<>(arr, pageable, totalCount);
    }

    @Override
    public Page<Object[]> getListSearch(char[] typeArr, String keyword, Pageable pageable) {

        QSaleBoard saleBoard = QSaleBoard.saleBoard;
        QSaleFavorite qSaleFavorite = QSaleFavorite.saleFavorite;
        QSalePicture salePicture = new QSalePicture("pic");

        JPQLQuery<SaleBoard> query = from(saleBoard);
        query.leftJoin(saleBoard.tags);
        query.leftJoin(saleBoard.pictures,salePicture);
        query.leftJoin(qSaleFavorite).on(qSaleFavorite.saleBoard.eq(saleBoard));



        if (typeArr != null && typeArr.length > 0) {
            BooleanBuilder builder = new BooleanBuilder();
            for (char type : typeArr) {
                if (type == 'T') {
                    builder.or(saleBoard.stitle.contains(keyword));
                } else if (type == 'C') {
                    builder.or(saleBoard.scontent.contains(keyword));
                } else if (type == 'W') {
                    builder.or(saleBoard.swriter.contains(keyword));
                } else if (type == 'E') {  //카테고리검색
                    builder.or(saleBoard.scategory.contains(keyword));
                }
            }
            query.where(builder);

        }
        query.where(saleBoard.sno.gt(0L)); //bno > 0
        query.where(salePicture.idx.eq(0));
        query.groupBy(saleBoard);

        JPQLQuery<Tuple> selectQuery = query.select(saleBoard.sno,saleBoard.tags.any(),salePicture,
                qSaleFavorite.score.sum());
        getQuerydsl().applyPagination(pageable, query);


        List<Tuple> tupleList = selectQuery.fetch();


        long totalCount = selectQuery.fetchCount();

        List<Object[]> arr = tupleList.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

        return new PageImpl<>(arr, pageable, totalCount);


    }
}


