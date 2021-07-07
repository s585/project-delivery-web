package tech.itpark.project_delivery_web.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.dto.ProductDto;
import tech.itpark.project_delivery_web.mappers.ProductMapper;
import tech.itpark.project_delivery_web.model.Product;
import tech.itpark.project_delivery_web.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> findAllByVendorId(Long id, String token) {
        return productRepository.findAllByVendorIdNative(id).stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Long id, String token) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find product by passed id: " + id));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto create(ProductDto dto) {
        final Product product = productMapper.toEntity(dto);
        final Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    public ProductDto update(ProductDto dto) {
        final Product product = productMapper.toEntity(dto);
        final Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id, String token) {
        productRepository.deleteById(id);
    }
}
